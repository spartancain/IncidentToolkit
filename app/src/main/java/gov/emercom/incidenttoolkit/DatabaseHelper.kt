package gov.emercom.incidenttoolkit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import gov.emercom.incidenttoolkit.main.IncidentList
import java.time.Instant
import kotlin.apply
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "incident.db", null, 1) {

     val INCIDENT_TABLE = "INCIDENT_TABLE"
     val COL_ID = "COLUMN_ID"
     val COL_INCIDENT_NAME = "INCIDENT_NAME"
     var COL_INCIDENT_TYPE = "INCIDENT_TYPE"
     var COL_INCIDENT_LOC = "INCIDENT_LOC"
     val COL_START = "START"

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement = buildString {
            append("CREATE TABLE ")
            append(INCIDENT_TABLE)
            append("(")
            append(COL_ID)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_INCIDENT_NAME)
            append(" TEXT, ")
            append(COL_INCIDENT_TYPE)
            append(" TEXT, ")
            append(COL_INCIDENT_LOC)
            append(" TEXT, ")
            append(COL_START)
            append(" TEXT)")
        }

        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS INCIDENT_TABLE")
    }

    fun insertIncident(incident: IncidentList): Boolean {
        val db = this.writableDatabase
        val timestamp = Instant.now().toEpochMilli()
        val values = ContentValues().apply {
            put(COL_INCIDENT_NAME, incident.incidentName)
            put(COL_INCIDENT_TYPE, incident.incidentType)
            put(COL_INCIDENT_LOC, incident.incidentLoc)
            put(COL_START, timestamp)
        }
        val insert = db.insert(INCIDENT_TABLE, null, values)
        return insert.toInt() != -1
        db.close()
    }

    //called by displayIncident@MainActivity for RecyclerView
    fun getIncidentList(): Cursor {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $INCIDENT_TABLE ORDER BY $COL_ID DESC",null)
        return cursor
    }

    fun getIncidentTypes(): List<String>{
        val returnList = mutableListOf<String>()
        val queryString = "SELECT $COL_INCIDENT_TYPE FROM $INCIDENT_TABLE"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst())
                return returnList
        return returnList
    }


    fun deleteIncident(incident: Int): Boolean {
        //find model in DB, if found delete and return true. If not found, return false.
        val db = this.writableDatabase
        val queryString = "DELETE FROM $INCIDENT_TABLE WHERE $COL_ID = $incident"
        val cursor = db.rawQuery(queryString, null)
        return cursor.moveToFirst()
        cursor.close()
        db.close()
    }

    fun updateIncidentField(keyColumn: String, keyValue: String, targetColumn: String, targetValue: String) {
        val db = this.writableDatabase
        val queryString = "UPDATE $INCIDENT_TABLE SET $targetColumn='$targetValue' WHERE $keyColumn='$keyValue'"
        Log.i("updateIncidentField",queryString)
        db.execSQL(queryString)
        db.close()
    }


    fun getSelectedIncident(incident: Int): ArrayList<IncidentList> {
        val db = this.readableDatabase
        val incidentArray = ArrayList<IncidentList>()
        val queryString = "SELECT * FROM $INCIDENT_TABLE WHERE $COL_ID = $incident"
        val cursor = db.rawQuery(queryString,null)
        if (cursor.moveToFirst()){
            do{
                incidentArray.add(IncidentList(
                    incidentID = cursor.getInt(0),
                    incidentName = cursor.getString(1),
                    incidentType = cursor.getString(2),
                    incidentLoc = cursor.getString(3),
                    incidentStart = cursor.getLong(4),
                    incidentStartDTG = FormatDate(cursor.getLong(4))
                ))
            } while (cursor.moveToNext())
        }
        return incidentArray
    }


}