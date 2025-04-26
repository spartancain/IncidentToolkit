package gov.emercom.incidenttoolkit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.util.Log
import gov.emercom.incidenttoolkit.main.IncidentGetList
import gov.emercom.incidenttoolkit.main.IncidentPutList
import java.io.ByteArrayOutputStream
import java.time.Instant

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "incident.db", null, 5) {

    //Incident Table Values
    val INCIDENT_TABLE = "INCIDENT_TABLE"
    val COL_ID = "COLUMN_ID"
    val COL_INCIDENT_NAME = "INCIDENT_NAME"
    val COL_INCIDENT_TYPE = "INCIDENT_TYPE"
    val COL_INCIDENT_LOC = "INCIDENT_LOC"
    val COL_START = "START"
    val COL_END = "END"
    val COL_SITU = "SITUATION"
    val COL_OBJS = "OBJECTIVES"
    val COL_EMPHASIS = "CMD_EMPHASIS"
    val COL_SA = "SA"
    val COL_SAFETYPLANREQ = "SAFETYPLANREQ"
    val COL_SAFETYPLANLOC = "SAFETYPLANLOC"
    val COL_RAD_INSTRUCTIONS = "RAD_INSTRUCTIONS"
    val COL_INCIDENT_REF = "INCIDENT_REF"
    val COL_INCIDENT_MAPIMAGE = "INCIDENT_MAPIMAGE"

    //Organisation Table Values
    val ORGANISATION_TABLE = "ORGANISATION_TABLE"
    val COL_ORG_INDEX = "ORG_INDEX"
    val COL_ORG_POSITION = "ORG_POSITION"
    val COL_ORG_POSTYPE = "ORG_POSTYPE"

    //Persons Table Values
    val PERSONS_TABLE = "PERSONS_TABLE"
    val COL_PERS_INDEX = "PERS_INDEX"
    val COL_PERS_NAME = "PERS_NAME"
    val COL_PERS_TITLE = "PERS_TITLE"
    val COL_PERS_PHONE = "PERS_PHONE"
    val COL_PERS_CS = "PERS_CS"

    //Radio Table Values
    val RADIO_TABLE = "RADIO_TABLE"
    val COL_RAD_INDEX = "RAD_INDEX"
    val COL_RAD_CHANNEL = "RAD_CHANNEL"
    val COL_RAD_POSITION = "RAD_POSITION"
    val COL_RAD_FUNCTION = "RAD_FUNCTION"
    val COL_RAD_MODE = "RAD_MODE"
    val COL_RAD_RX_FREQ = "RAD_RX_FREQ"
    val COL_RAD_RX_TONE = "RAD_RX_TONE"
    val COL_RAD_TX_FREQ = "RAD_TX_FREQ"
    val COL_RAD_TX_TONE = "RAD_TX_TONE"
    val COL_RAD_REMARKS = "RAD_REMARKS"

    //Timeline Table Values
    val TIMELINE_TABLE = "TIMELINE_TABLE"
    val COL_TIME_INDEX = "TIME_INDEX"
    val COL_TIME_PERIOD_START = "TIME_PERIOD_START"
    val COL_TIME_PERIOD_END = "TIME_PERIOD_END"
    val COL_TIME_PERIOD_REF = "TIME_PERIOD_REF"

    override fun onCreate(db: SQLiteDatabase?) {
        val incidentTableCreator = buildString {
            append("CREATE TABLE ")
            append(INCIDENT_TABLE)
            append("(")
            append(COL_ID) //COL 0
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_INCIDENT_NAME) //COL 1
            append(" TEXT, ")
            append(COL_INCIDENT_TYPE) //COL 2
            append(" TEXT, ")
            append(COL_INCIDENT_LOC) //COL 3
            append(" TEXT, ")
            append(COL_START) //COL 4
            append(" TEXT, ")
            append(COL_END) //COL 5
            append(" TEXT, ")
            append(COL_SITU) //COL 6
            append(" TEXT, ")
            append(COL_OBJS) //COL 7
            append(" TEXT, ")
            append(COL_EMPHASIS) //COL 8
            append(" TEXT, ")
            append(COL_SA) //COL 9
            append(" TEXT, ")
            append(COL_SAFETYPLANREQ) //COL 10
            append(" INTEGER, ")
            append(COL_SAFETYPLANLOC) //COL 11
            append(" TEXT, ")
            append(COL_RAD_INSTRUCTIONS) //COL 12
            append(" TEXT, ")
            append(COL_INCIDENT_REF) //COL 13
            append(" TEXT, ")
            append(COL_INCIDENT_MAPIMAGE) //COL 14
            append(" BLOB)")
        }
        Log.i("IncidentTableCreator", incidentTableCreator)
        db?.execSQL(incidentTableCreator)

        val organisationTableCreator = buildString {
            append("CREATE TABLE ")
            append(ORGANISATION_TABLE)
            append("(")
            append(COL_ORG_INDEX)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_ORG_POSITION)
            append(" TEXT, ")
            append(COL_ORG_POSTYPE)
            append(" TEXT, ")
            append(COL_ID)
            append(" INTEGER)")
        }
        db?.execSQL(organisationTableCreator)

        val personsTableCreator = buildString {
            append("CREATE TABLE ")
            append(PERSONS_TABLE)
            append("(")
            append(COL_PERS_INDEX)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_PERS_NAME)
            append(" TEXT, ")
            append(COL_PERS_TITLE)
            append(" TEXT, ")
            append(COL_ORG_POSITION)
            append(" TEXT, ")
            append(COL_PERS_PHONE)
            append(" TEXT, ")
            append(COL_RAD_CHANNEL)
            append(" TEXT, ")
            append(COL_PERS_CS)
            append(" TEXT, ")
            append(COL_ID)
            append(" INTEGER)")
        }
        db?.execSQL(personsTableCreator)

        val radioTableCreator = buildString {
            append("CREATE TABLE ")
            append(RADIO_TABLE)
            append("(")
            append(COL_RAD_INDEX)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_RAD_CHANNEL)
            append(" TEXT, ")
            append(COL_RAD_POSITION)
            append(" TEXT, ")
            append(COL_RAD_FUNCTION)
            append(" TEXT, ")
            append(COL_RAD_MODE)
            append(" TEXT, ")
            append(COL_RAD_RX_FREQ)
            append(" TEXT, ")
            append(COL_RAD_RX_TONE)
            append(" TEXT, ")
            append(COL_RAD_TX_FREQ)
            append(" TEXT, ")
            append(COL_RAD_TX_TONE)
            append(" TEXT, ")
            append(COL_RAD_REMARKS)
            append(" TEXT, ")
            append(COL_ID)
            append(" INTEGER)")
        }
        db?.execSQL(radioTableCreator)

        val timelineTableCreator = buildString {
            append("CREATE TABLE ")
            append(TIMELINE_TABLE)
            append("(")
            append(COL_TIME_INDEX)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_TIME_PERIOD_START)
            append(" TEXT, ")
            append(COL_TIME_PERIOD_END)
            append(" TEXT, ")
            append(COL_TIME_PERIOD_REF)
            append(" TEXT, ")
            append(COL_ID)
            append(" INTEGER)")
        }
        db?.execSQL(timelineTableCreator)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS INCIDENT_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS ORGANISATION_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS PERSONS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS RADIO_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS TIMELINE_TABLE")
        onCreate(db)
    }

    //INCIDENT ACTIONS
    fun insertIncident(incident: IncidentPutList): Boolean {
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
        val cursor = db.rawQuery("SELECT * FROM $INCIDENT_TABLE ORDER BY $COL_ID DESC", null)
        return cursor
    }

    fun getIncidentTypes(): List<String> {
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

    fun updateIncidentField(
        keyColumn: String,
        keyValue: String,
        targetColumn: String,
        targetValue: String
    ) {
        val db = this.writableDatabase
        val queryString =
            "UPDATE $INCIDENT_TABLE SET $targetColumn='$targetValue' WHERE $keyColumn='$keyValue'"
        Log.i("updateIncidentField", queryString)
        db.execSQL(queryString)
        db.close()
    }

    fun getSelectedIncident(incident: Int): ArrayList<IncidentGetList> {
        val db = this.readableDatabase
        val incidentArray = ArrayList<IncidentGetList>()
        val queryString = "SELECT * FROM $INCIDENT_TABLE WHERE $COL_ID = $incident"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                incidentArray.add(
                    IncidentGetList(
                        incidentID = cursor.getInt(0),
                        incidentName = cursor.getString(1),
                        incidentType = cursor.getString(2),
                        incidentLoc = cursor.getString(3),
                        incidentStart = cursor.getLong(4),
                        incidentStartDTG = FormatDate(cursor.getLong(4)),
                        incidentMapImage = getIncidentMapByteArray(
                            COL_ID,
                            cursor.getInt(0).toString()
                        )
                    )
                )
            } while (cursor.moveToNext())
        }
        return incidentArray
    }

    fun updateIncidentMapImage(
        keyColumn: String,
        keyValue: String,
        targetColumn: String,
        targetImage: Bitmap
    ) {
        val db = this.writableDatabase
        val targetArray2 = bitmapToByteArray(targetImage)
        val values = ContentValues().apply {
            put(targetColumn, targetArray2)
        }
        db.update("INCIDENT_TABLE", values, "$keyColumn = ?", arrayOf(keyValue))
//        Log.i("updateIncidentMapImage2", targetArray2.size.toString())
//        Log.i("updateIncidentMapImage2", targetArray2.contentToString())
        db.close()
    }

    fun bitmapToByteArray(
        bitmap: Bitmap,
        format: CompressFormat = CompressFormat.PNG,
        quality: Int = 100
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(format, quality, stream)
        val byteArray = stream.toByteArray()
        return byteArray
    }

    fun getIncidentMapBitmap(keyColumn: String, keyValue: String): Bitmap? {
        val db = this.readableDatabase
        val queryString =
            "SELECT $COL_INCIDENT_MAPIMAGE FROM $INCIDENT_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        cursor.use {
            if (it.moveToFirst()) {
                val byteArray = it.getBlob(it.getColumnIndexOrThrow(COL_INCIDENT_MAPIMAGE))
                byteArray?.let {
                    //Log.i("getIncidentMapImage", byteArray.contentToString())
                    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                }
            }
        }
        return null
    }

    fun getIncidentMapByteArray(keyColumn: String, keyValue: String): ByteArray {
        val db = this.readableDatabase
        val queryString =
            "SELECT $COL_INCIDENT_MAPIMAGE FROM $INCIDENT_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        val defaultImage = createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        var byteArray: ByteArray = bitmapToByteArray(defaultImage)
        if (cursor.moveToFirst()) {
            byteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COL_INCIDENT_MAPIMAGE))

        }
        return byteArray
    }
}