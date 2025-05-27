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
import android.widget.Toast
import androidx.core.database.getStringOrNull
import gov.emercom.incidenttoolkit.main.IncidentGetList
import gov.emercom.incidenttoolkit.main.IncidentPutList
import gov.emercom.incidenttoolkit.main.OrgChartList
import gov.emercom.incidenttoolkit.main.OrgList
import gov.emercom.incidenttoolkit.main.PersFullList
import gov.emercom.incidenttoolkit.main.PersMinList
import gov.emercom.incidenttoolkit.main.RadioList
import gov.emercom.incidenttoolkit.main.TimelineList
import java.io.ByteArrayOutputStream
import java.time.Instant

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "incident.db", null, 7) {

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
        //Log.i("IncidentTableCreator", incidentTableCreator)
        db?.execSQL(incidentTableCreator)

        val organisationTableCreator = buildString {
            append("CREATE TABLE ")
            append(ORGANISATION_TABLE)
            append("(")
            append(COL_ORG_INDEX) //COL 0
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_ORG_POSITION) //COL 1
            append(" TEXT, ")
            append(COL_ORG_POSTYPE) //COL 2
            append(" TEXT, ")
            append(COL_PERS_NAME) //COL 3
            append(" TEXT, ")
            append(COL_ID) //COL 4
            append(" INTEGER, ")
            append("FOREIGN KEY ($COL_ID) REFERENCES $INCIDENT_TABLE($COL_ID)")
            append(")")
        }
        db?.execSQL(organisationTableCreator)

        val personsTableCreator = buildString {
            append("CREATE TABLE ")
            append(PERSONS_TABLE)
            append("(")
            append(COL_PERS_INDEX) //COL 0
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_PERS_NAME) //COL 1
            append(" TEXT, ")
            append(COL_PERS_TITLE) //COL 2
            append(" TEXT, ")
            append(COL_ORG_POSITION) //COL 3
            append(" TEXT, ")
            append(COL_PERS_PHONE) //COL 4
            append(" TEXT, ")
            append(COL_RAD_CHANNEL) //COL 5
            append(" TEXT, ")
            append(COL_PERS_CS) //COL 6
            append(" TEXT, ")
            append(COL_ID) //COL 7
            append(" INTEGER, ")
            append("FOREIGN KEY ($COL_ID) REFERENCES $INCIDENT_TABLE($COL_ID)")
            append(")")
        }
        db?.execSQL(personsTableCreator)

        val radioTableCreator = buildString {
            append("CREATE TABLE ")
            append(RADIO_TABLE)
            append("(")
            append(COL_RAD_INDEX) //COL 0
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_RAD_CHANNEL) //COL 1
            append(" TEXT, ")
            append(COL_RAD_POSITION) //COL 2
            append(" TEXT, ")
            append(COL_RAD_FUNCTION) //COL 3
            append(" TEXT, ")
            append(COL_RAD_MODE) //COL 4
            append(" TEXT, ")
            append(COL_RAD_RX_FREQ) //COL 5
            append(" TEXT, ")
            append(COL_RAD_RX_TONE) //COL 6
            append(" TEXT, ")
            append(COL_RAD_TX_FREQ) //COL 7
            append(" TEXT, ")
            append(COL_RAD_TX_TONE) //COL 8
            append(" TEXT, ")
            append(COL_RAD_REMARKS) //COL 9
            append(" TEXT, ")
            append(COL_ID) //COL 10
            append(" INTEGER, ")
            append("FOREIGN KEY ($COL_ID) REFERENCES $INCIDENT_TABLE($COL_ID)")
            append(")")
        }
        db?.execSQL(radioTableCreator)

        val timelineTableCreator = buildString {
            append("CREATE TABLE ")
            append(TIMELINE_TABLE)
            append("(")
            append(COL_TIME_INDEX) //COL 0
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(COL_TIME_PERIOD_START) //COL 1
            append(" TEXT, ")
            append(COL_TIME_PERIOD_END) //COL 2
            append(" TEXT, ")
            append(COL_TIME_PERIOD_REF) //COL 3
            append(" TEXT, ")
            append(COL_ID) //COL 4
            append(" INTEGER, ")
            append("FOREIGN KEY ($COL_ID) REFERENCES $INCIDENT_TABLE($COL_ID)")
            append(")")
        }
        db?.execSQL(timelineTableCreator)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (oldVersion < 3) {
//            db?.execSQL("ALTER TABLE ${TODO()} ADD COLUMN ${TODO()} TEXT")
        } else {
            db?.execSQL("DROP TABLE IF EXISTS INCIDENT_TABLE")
            db?.execSQL("DROP TABLE IF EXISTS ORGANISATION_TABLE")
            db?.execSQL("DROP TABLE IF EXISTS PERSONS_TABLE")
            db?.execSQL("DROP TABLE IF EXISTS RADIO_TABLE")
            db?.execSQL("DROP TABLE IF EXISTS TIMELINE_TABLE")
            onCreate(db)
        }
    }

    //GENERAL ACTIONS
    //called by displayIncident@MainActivity for RecyclerView
    fun getTableContentsDescending(table: String, orderColumn: String): Cursor {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $table ORDER BY $orderColumn DESC", null)
        return cursor
    }

    //Called by Timeline/actions RecyclerViews
    fun getMatchingContentsAscending(table: String, keyColumn: String, keyValue: String, orderColumn: String): Cursor {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $table WHERE $keyColumn='$keyValue' ORDER BY $orderColumn ASC", null)
        return cursor
    }

    fun getSingleColumnAll(table: String, keyColumn: String): Array<String> {
        val db = this.readableDatabase
        val queryString = "SELECT $keyColumn FROM $table"
        val cursor: Cursor = db.rawQuery(queryString, null)
        val typeList = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val type = cursor.getString(cursor.getColumnIndexOrThrow(keyColumn))
                typeList.add(type)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return typeList.toTypedArray()
    }

    fun updateField(
        table: String,
        keyColumn: String,
        keyValue: String,
        targetColumn: String,
        targetValue: String
    ) {
        val db = this.writableDatabase
        val queryString =
            "UPDATE $table SET $targetColumn='$targetValue' WHERE $keyColumn='$keyValue'"
        Log.i("updateIncidentField", queryString)
        db.execSQL(queryString)
        db.close()
    }


    fun deleteRecord(table: String, keyColumn: String, keyValue: Int): Boolean {
        //find model in DB, if found delete and return true. If not found, return false.
        val db = this.writableDatabase
        val queryString = "DELETE FROM $table WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        return cursor.moveToFirst()
        cursor.close()
        db.close()
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
                        incidentEndDTG = FormatDate(cursor.getLong(5)),
                        incidentSituation = cursor.getString(6),
                        incidentObjectives = cursor.getString(7),
                        incidentEmphasis = cursor.getString(8),
                        incidentSA = cursor.getString(9),
                        incidentSafetyPlanReq = cursor.getInt(10),
                        incidentSafetyPlanLoc = cursor.getString(11),
                        incidentRadInstructions = cursor.getString(12),
                        incidentRef = cursor.getString(13),
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

    fun updateIncidentMapImage(context: Context, keyColumn: String, keyValue: String, targetColumn: String, targetImage: Bitmap) {
        val db = this.writableDatabase
        val targetArray2 = bitmapToByteArray(targetImage)
        val maxImageBytes = 1000000 //Max cursor size is 1MB
        if (targetArray2.size < maxImageBytes) {
            val values = ContentValues().apply {
                put(targetColumn, targetArray2)
            }
            db.update("INCIDENT_TABLE", values, "$keyColumn = ?", arrayOf(keyValue))
//        Log.i("updateIncidentMapImage2", targetArray2.size.toString())
//        Log.i("updateIncidentMapImage2", targetArray2.contentToString())
            db.close()
            return
        } else {
            Toast.makeText(context,"Image Too Large. 1MB Max Size, Unfortunately.",Toast.LENGTH_SHORT).show()
            return
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap, format: CompressFormat = CompressFormat.PNG, quality: Int = 100): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(format, quality, stream)
        val byteArray = stream.toByteArray()
        return byteArray
    }

    //Unused but leaving it for reference
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

    //ORGANISATION ACTIONS
    fun insertOrgPosition(orgList: OrgList): Boolean {
        val db = this.writableDatabase
        val orgPosRead = ArrayList<OrgList?>()
        val queryString = "SELECT * FROM $ORGANISATION_TABLE WHERE $COL_ORG_POSITION = '${orgList.orgPosition}' AND $COL_ID = ${orgList.orgIncidentID}"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            updateField(ORGANISATION_TABLE,COL_ORG_POSITION,orgList.orgPosition,COL_PERS_NAME,orgList.orgPersName)
            cursor.close()
            return true
        } else {
            val values = ContentValues().apply {
                put(COL_ORG_POSITION, orgList.orgPosition)
                put(COL_ORG_POSTYPE, orgList.orgPosType)
                put(COL_PERS_NAME, orgList.orgPersName)
                put(COL_ID, orgList.orgIncidentID)
            }
            val insert = db.insert(ORGANISATION_TABLE, null, values)
            return insert.toInt() != -1
        }
        db.close()
    }

/*    fun upsertOrg(orgList: OrgList) {
        val db = this.writableDatabase
        val columns = "$COL_ORG_POSITION, $COL_ORG_POSTYPE, $COL_PERS_NAME, $COL_ID"
        val values = "'${orgList.orgPosition}', '${orgList.orgPosType}', '${orgList.orgPersName}', ${orgList.orgIncidentID}"
        val queryString = """
            INSERT INTO $ORGANISATION_TABLE ($columns) VALUES ($values)
            ON CONFLICT ('$COL_ORG_POSITION', $COL_ID) DO UPDATE SET
            $COL_PERS_NAME = ${orgList.orgPersName}
            """
        Log.i("upsertOrg",queryString)
        db.execSQL(queryString)
        db.close()
    }*/

    fun getSelectedOrgFull(keyColumn: String, keyValue: String): ArrayList<OrgList> {
        val db = this.readableDatabase
        val orgArray = ArrayList<OrgList>()
        val queryString = "SELECT * FROM $ORGANISATION_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                orgArray.add(
                    OrgList(
                        orgIndex = cursor.getInt(0),
                        orgPosition = cursor.getString(1),
                        orgPosType = cursor.getString(2),
                        orgPersName = cursor.getString(3),
                        orgIncidentID = cursor.getInt(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        return orgArray
    }

    fun getSelectedOrgForChart(incidentID: Int): ArrayList<OrgChartList> {
        val db = this.readableDatabase
        val orgArray = ArrayList<OrgChartList>()
        val queryString = "SELECT * FROM $ORGANISATION_TABLE WHERE $COL_ID = $incidentID"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                orgArray.add(
                    OrgChartList(
                        orgPosition = cursor.getString(1),
                        orgPersName = cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        return orgArray
    }

    //PERSONS ACTIONS
    fun insertPersonFull(persList: PersFullList): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PERS_NAME, persList.persName)
            put(COL_PERS_TITLE, persList.persTitle)
            put(COL_ORG_POSITION, persList.persPosition)
            put(COL_PERS_PHONE, persList.persPhone)
            put(COL_RAD_CHANNEL, persList.persRadChannel)
            put(COL_PERS_CS, persList.persCallsign)
            put(COL_ID, persList.persIncidentID)
        }
        val insert = db.insert(PERSONS_TABLE, null, values)
        return insert.toInt() != -1
        db.close()
    }

    fun insertPersonBasic(persList: PersMinList): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PERS_NAME, persList.persName)
            put(COL_ORG_POSITION, persList.persPosition)
            put(COL_ID, persList.persIncidentID)
        }
        val queryString = "SELECT * FROM $PERSONS_TABLE WHERE $COL_PERS_NAME = '${persList.persName}' AND $COL_ID = ${persList.persIncidentID}"
        val cursor = db.rawQuery(queryString,null)
        if (cursor.moveToFirst()) {
            val whereClause = "$COL_PERS_NAME = ? AND $COL_ID = ?"
            val whereArgs = arrayOf<String>(persList.persName,persList.persIncidentID.toString())
            val update = db.update(PERSONS_TABLE,values,whereClause,whereArgs)
            val updateResult = update.toInt() != -1
            cursor.close()
            return updateResult
        } else {
            val insert = db.insert(PERSONS_TABLE, null, values)
            return insert.toInt() != -1
        }
        db.close()
    }

/*    fun upsertPersFromOrg(persList: PersMinList) {
        val db = this.writableDatabase
        val columns = "$COL_PERS_NAME, $COL_ORG_POSITION, $COL_ID"
        val values = "${persList.persName}, ${persList.persPosition}, ${persList.persIncidentID}"
        val queryString = """
            INSERT INTO $PERSONS_TABLE ($columns) VALUES ($values)
            ON CONFLICT ($COL_PERS_NAME, $COL_ID) DO UPDATE SET
            $COL_ORG_POSITION = ${persList.persPosition}
            """
        db.execSQL(queryString)
        db.close()
    }*/

    fun getSelectedPersons(keyColumn: String, keyValue: String): ArrayList<PersFullList> {
        val db = this.readableDatabase
        val persArray = ArrayList<PersFullList>()
        val queryString = "SELECT * FROM $PERSONS_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                persArray.add(
                    PersFullList(
                        persIndex = cursor.getInt(0),
                        persName = cursor.getString(1),
                        persTitle = cursor.getStringOrNull(2).toString(),
                        persPosition = cursor.getString(3),
                        persPhone = cursor.getStringOrNull(4).toString(),
                        persRadChannel = cursor.getStringOrNull(5).toString(),
                        persCallsign = cursor.getStringOrNull(6).toString(),
                        persIncidentID = cursor.getInt(7)
                    )
                )
            } while (cursor.moveToNext())
        }
        return persArray
    }

    //RADIO ACTIONS
    fun insertRadio(radioList: RadioList): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_RAD_CHANNEL, radioList.radChannel)
            put(COL_RAD_POSITION, radioList.radPosition)
            put(COL_RAD_FUNCTION, radioList.radFunction)
            put(COL_RAD_MODE, radioList.radMode)
            put(COL_RAD_RX_FREQ, radioList.radRxFreq)
            put(COL_RAD_RX_TONE, radioList.radRxTone)
            put(COL_RAD_TX_FREQ, radioList.radTxFreq)
            put(COL_RAD_TX_TONE, radioList.radTxTone)
            put(COL_RAD_REMARKS, radioList.radRemarks)
            put(COL_ID, radioList.radIncidentID)
        }
        val insert = db.insert(RADIO_TABLE, null, values)
        return insert.toInt() != -1
        db.close()
    }

    fun getSelectedRadio(keyColumn: String, keyValue: String): ArrayList<RadioList> {
        val db = this.readableDatabase
        val radioArray = ArrayList<RadioList>()
        val queryString = "SELECT * FROM $RADIO_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                radioArray.add(
                    RadioList(
                        radIndex = cursor.getInt(0),
                        radChannel = cursor.getString(1),
                        radPosition = cursor.getString(2),
                        radFunction = cursor.getString(3),
                        radMode = cursor.getString(4),
                        radRxFreq = cursor.getString(5),
                        radRxTone = cursor.getString(6),
                        radTxFreq = cursor.getString(7),
                        radTxTone = cursor.getString(8),
                        radRemarks = cursor.getString(9),
                        radIncidentID = cursor.getInt(10)
                    )
                )
            } while (cursor.moveToNext())
        }
        return radioArray
    }

    //TIMELINE ACTIONS
    fun insertTimeline(timelineList: TimelineList): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_TIME_INDEX, timelineList.timeIndex)
            put(COL_TIME_PERIOD_START, timelineList.timePeriodStart)
            put(COL_TIME_PERIOD_END, timelineList.timePeriodEnd)
            put(COL_TIME_PERIOD_REF, timelineList.timePeriodRef)
            put(COL_ID, timelineList.timeIncidentID)
        }
        val insert = db.insert(TIMELINE_TABLE, null, values)
        return insert.toInt() != -1
        db.close()
    }

    fun upserleteTimeline(context: Context, timelineList: ArrayList<TimelineList>) {
        val db = this.writableDatabase
        try {
            for (data in timelineList) {
                val values = ContentValues().apply {
                    put(COL_TIME_PERIOD_START, data.timePeriodStart)
                    put(COL_TIME_PERIOD_END, data.timePeriodEnd)
                    put(COL_TIME_PERIOD_REF, data.timePeriodRef)
                    put(COL_ID, data.timeIncidentID)
                }
                //If timeindex doesn't exist yet do insert if long enough
                if (data.timeIndex < 0) {
                    if (data.timePeriodStart.length > 3 && data.timePeriodRef.length > 3) {
                        Log.i("upsertTimeline",values.toString())
                        val insert = db.insert(TIMELINE_TABLE, null, values)
                        Log.i("insertTimeline","Added Row $insert to DB")
                    } else {
                        Toast.makeText(context,"A timeline value was too short to save: 4 characters minimum.",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    //If start and contents are empty delete entry
                    if (data.timePeriodStart.isEmpty() && data.timePeriodRef.isEmpty()) {
                        db.delete(
                            TIMELINE_TABLE,
                            "$COL_TIME_INDEX = ?",
                            arrayOf(data.timeIndex.toString())
                        )
                    } else {
                        //If otherwise do update entry
                        db.update(
                            TIMELINE_TABLE,
                            values,
                            "$COL_TIME_INDEX = ?",
                            arrayOf(data.timeIndex.toString())
                        )
                    }
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun getSelectedTimeline(keyColumn: String, keyValue: String): ArrayList<TimelineList> {
        val db = this.readableDatabase
        val timelineArray = ArrayList<TimelineList>()
        val queryString = "SELECT * FROM $TIMELINE_TABLE WHERE $keyColumn = $keyValue"
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                timelineArray.add(
                    TimelineList(
                        timeIndex = cursor.getInt(0),
                        timePeriodStart = cursor.getString(1),
                        timePeriodEnd = cursor.getString(2),
                        timePeriodRef = cursor.getString(3),
                        timeIncidentID = cursor.getInt(4),
                        -1
                    )
                )
            } while (cursor.moveToNext())
        }
        return timelineArray
    }

}