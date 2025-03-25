package gov.emercom.incidenttoolkit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.Instant
import kotlin.apply

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
    }

    //called by displayIncident@MainActivity for RecyclerView
    fun getIncidentList(): Cursor {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $INCIDENT_TABLE ORDER BY $COL_ID DESC",null)
        cursor.close()
        db.close()
        return cursor
    }


    fun getAllIncidents(): List<String> {

        val returnList = mutableListOf<String>()
        val queryString = "SELECT * FROM $INCIDENT_TABLE ORDER BY $COL_ID DESC"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(queryString, null)

        if (cursor != null) {
            if (cursor.moveToFirst()){
                // loop through the cursor and put them into return list
                do {
                    val incidentID: Int = cursor.getInt(0)
                    val incidentName: String = cursor.getString(1)
                    val incidentType: String = cursor.getString(2)
                    val incidentLoc: String = cursor.getString(3)
                    val incidentStart: Long = cursor.getLong(4)
                    val incidentStartDTG: String = FormatDate(incidentStart)

                    val newIncident = IncidentList(incidentID,incidentName,incidentType,incidentLoc,incidentStart, incidentStartDTG)
                    returnList.add(newIncident.toString())

                } while (cursor.moveToNext())

            }
            else {
                //on fail do not add anything to the list
            }

            cursor.close()
            db.close()
        }

        return returnList
    }

    fun getIncidentTypes(): List<String>{
        val returnList = mutableListOf<String>()
        val queryString = "SELECT $COL_INCIDENT_TYPE FROM $INCIDENT_TABLE"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(queryString, null)

        if (cursor != null) {
            if (cursor.moveToFirst())
                return returnList
        }
        cursor.close()
        db.close()
        return returnList
    }

    fun deleteIncident(incident: Int): Boolean {
        //find model in DB, if found delete and return true. If not found, return false.
        val db = this.writableDatabase
        val queryString = "DELETE FROM $INCIDENT_TABLE WHERE $COL_ID = $incident"

        val cursor = db.rawQuery(queryString, null)
        cursor.close()
        db.close()
        return cursor.moveToFirst()
    }


}