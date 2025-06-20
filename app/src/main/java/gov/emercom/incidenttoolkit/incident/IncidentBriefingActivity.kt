package gov.emercom.incidenttoolkit.incident

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.main.IncidentGetList
import gov.emercom.incidenttoolkit.main.OrgChartList
import gov.emercom.incidenttoolkit.main.OrgList
import gov.emercom.incidenttoolkit.main.PersMinList
import gov.emercom.incidenttoolkit.main.TimelineList

class IncidentBriefingActivity: AppCompatActivity() {

    private var incidentID: Int = -1
    private lateinit var tIncidentName3: TextView
    private lateinit var tIncidentID3: TextView
    private lateinit var tIncidentStart3: TextView
    private lateinit var ivIncidentMapImage: ImageView
    private lateinit var etBriefingSituationSummary: AutoCompleteTextView
    private lateinit var etBriefingObjectives: AutoCompleteTextView
    private lateinit var etBriefingOrgIC: AutoCompleteTextView
    private lateinit var etBriefingOrgLiaison: AutoCompleteTextView
    private lateinit var etBriefingOrgSafety: AutoCompleteTextView
    private lateinit var etBriefingOrgPIO: AutoCompleteTextView
    private lateinit var etBriefingOrgOpsChief: AutoCompleteTextView
    private lateinit var etBriefingOrgPlanChief: AutoCompleteTextView
    private lateinit var etBriefingOrgLogsChief: AutoCompleteTextView
    private lateinit var etBriefingOrgFinanceChief: AutoCompleteTextView
    private lateinit var bBriefingSaveRemain: Button
    private lateinit var bBriefingSaveExit: Button
    private lateinit var rvTimelineList: RecyclerView
    private lateinit var bAddActionRow: Button
    private lateinit var timelineArray: ArrayList<TimelineList>

    val dbh = DatabaseHelper(this@IncidentBriefingActivity)
    val orgChartMap = mutableMapOf<AutoCompleteTextView, String>()

    //image picker
    private  var pickImageLauncher: ActivityResultLauncher<Intent> = registerForActivityResult (ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            imageUri?.let {
                val bitmapSource = ImageDecoder.createSource(contentResolver, it)
                val bitmap = ImageDecoder.decodeBitmap(bitmapSource)
                dbh.updateIncidentMapImage(
                    this@IncidentBriefingActivity,
                    keyColumn = "COLUMN_ID",
                    keyValue = incidentID.toString(),
                    targetColumn = "INCIDENT_MAPIMAGE",
                    targetImage = bitmap
                )
                val savedImage = dbh.getIncidentMapByteArray(
                    keyColumn = "COLUMN_ID",
                    keyValue = incidentID.toString()
                )
                ivIncidentMapImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        savedImage,
                        0,
                        savedImage.size
                    )
                )
                Log.i("savedImage",savedImage.toString())
            }
            Toast.makeText(this@IncidentBriefingActivity,"Image Successfully Saved",Toast.LENGTH_SHORT).show()

        }
    }




    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_incident_briefing)
        supportActionBar?.hide()

        //Get selected incident ID from MainActivity and produce incident info arrays
        incidentID = intent.getIntExtra("selectedIncidentID",-1)
        val incidentRow = dbh.getSelectedIncident(incidentID)

        tIncidentName3 = findViewById(R.id.tIncidentName3)
        tIncidentID3 = findViewById(R.id.tIncidentID3)
        tIncidentStart3 = findViewById(R.id.tIncidentStart3)
        ivIncidentMapImage = findViewById(R.id.ivIncidentMapImage)
        etBriefingSituationSummary = findViewById(R.id.etBriefingSituationSummary)
        etBriefingObjectives = findViewById(R.id.etBriefingObjectives)
        etBriefingOrgIC = findViewById(R.id.etBriefingOrgIC)
        etBriefingOrgLiaison = findViewById(R.id.etBriefingOrgLiaison)
        etBriefingOrgSafety = findViewById(R.id.etBriefingOrgSafety)
        etBriefingOrgPIO = findViewById(R.id.etBriefingOrgPIO)
        etBriefingOrgOpsChief = findViewById(R.id.etBriefingOrgOpsChief)
        etBriefingOrgPlanChief = findViewById(R.id.etBriefingOrgPlanChief)
        etBriefingOrgLogsChief = findViewById(R.id.etBriefingOrgLogsChief)
        etBriefingOrgFinanceChief = findViewById(R.id.etBriefingOrgFinanceChief)
        bBriefingSaveRemain = findViewById(R.id.bBriefingSaveRemain)
        bBriefingSaveExit = findViewById(R.id.bBriefingSaveExit)
        rvTimelineList = findViewById(R.id.rvBriefingActions)
        bAddActionRow = findViewById(R.id.bAddActionRow)


        orgChartMap.put(etBriefingOrgIC, "Incident Commander")
        orgChartMap.put(etBriefingOrgLiaison, "Liaison Officer")
        orgChartMap.put(etBriefingOrgSafety, "Safety Officer")
        orgChartMap.put(etBriefingOrgPIO, "Public Information Officer")
        orgChartMap.put(etBriefingOrgOpsChief, "Operations Chief")
        orgChartMap.put(etBriefingOrgPlanChief, "Planning Chief")
        orgChartMap.put(etBriefingOrgLogsChief, "Logistics Chief")
        orgChartMap.put(etBriefingOrgFinanceChief, "Finance Chief")

        setIncidentArrayText(incidentRow)
        setOrgChartText()


        ivIncidentMapImage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
            return@setOnLongClickListener true
        }

        ivIncidentMapImage.setOnTouchListener { v, event ->
            val parent = v.parent
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    parent.requestDisallowInterceptTouchEvent(true)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    parent.requestDisallowInterceptTouchEvent(false)
                    true
                }
                else -> false
            }
        }

        //Timeline Recycler onCreate items
        rvTimelineList.layoutManager = LinearLayoutManager(this)
        rvTimelineList.setHasFixedSize(true)
        timelineArray = arrayListOf<TimelineList>()

        displayTimeline()

        bAddActionRow.setOnClickListener {
            timelineArray.add(
                TimelineList(
                    -1,
                    "",
                    "",
                    "",
                    incidentID,
                    -1
                )
            )
            val adapter = IncidentActionsRecyclerAdapter(timelineArray)
            rvTimelineList.adapter = adapter
        }

        bBriefingSaveRemain.setOnClickListener {

            if (etBriefingSituationSummary.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SITU,
                    etBriefingSituationSummary.text.toString()
                )
            }

            if (etBriefingObjectives.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_OBJS,
                    etBriefingObjectives.text.toString()
                )
            }

            saveOrgChartFields()
            setOrgChartText()

            dbh.upserleteTimeline(this@IncidentBriefingActivity, timelineArray)
            displayTimeline()

            Toast.makeText(this@IncidentBriefingActivity, "Incident Briefing Saved.", Toast.LENGTH_SHORT).show()
        }

        bBriefingSaveExit.setOnClickListener {

            if (etBriefingSituationSummary.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SITU,
                    etBriefingSituationSummary.text.toString()
                )
            }

            if (etBriefingObjectives.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_OBJS,
                    etBriefingObjectives.text.toString()
                )
            }

            saveOrgChartFields()
            setOrgChartText()
            dbh.upserleteTimeline(this@IncidentBriefingActivity, timelineArray)
            Toast.makeText(this@IncidentBriefingActivity, "Incident Briefing Saved.", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    private fun displayTimeline() {
        val newCursor: Cursor = dbh.getMatchingContentsAscending(dbh.TIMELINE_TABLE,dbh.COL_ID,incidentID.toString(),dbh.COL_TIME_PERIOD_START)
        timelineArray = ArrayList<TimelineList>()
        while (newCursor.moveToNext()){
            val uTimelineIndex = newCursor.getInt(0)
            val uTimelineStart = newCursor.getString(1)
            val uTimelineEnd = newCursor.getString(2)
            val uTimelineRef = newCursor.getString(3)
            val uTimelineIncidentID = newCursor.getInt(4)

            timelineArray.add(
                TimelineList(
                    uTimelineIndex,
                    uTimelineStart,
                    uTimelineEnd,
                    uTimelineRef,
                    uTimelineIncidentID,
                    -1
                )
            )
        }

        //Add one to whatever was loaded to create extra action line in initial create. -1 flag allows create of new in dbh
        timelineArray.add(
            TimelineList(
                -1,
                "",
                "",
                "",
                incidentID,
                -1
            )
        )

        val adapter = IncidentActionsRecyclerAdapter(timelineArray)
        rvTimelineList.adapter = adapter

/*        adapter.setOnItemLongClickListener(object: IncidentActionsRecyclerAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {

                for (i in 0 until timelineArray.size) {
                    if (i != position) {
                        timelineArray[i].isSelected = -1
                    } else {
                        timelineArray[i].isSelected = 1
                        Log.i ("ActionLongClick","Selected Position $i as isSelected ${timelineArray[i].isSelected}")
                    }
                }
            }
        })*/

    }

    fun setIncidentArrayText(arrayList: ArrayList<IncidentGetList>) {
        if (arrayList.isNotEmpty()) {
            val array = arrayList[0]
            val incidentName = array.incidentName
            val incidentStart = array.incidentStartDTG
            val incidentMapByteArray: ByteArray? = array.incidentMapImage
            val incidentMapImage = incidentMapByteArray?.size?.let {
                BitmapFactory.decodeByteArray(
                    incidentMapByteArray, 0,
                    it
                )
            }
            tIncidentID3.text = incidentID.toString()
            tIncidentName3.text = incidentName
            tIncidentStart3.text = incidentStart
            ivIncidentMapImage.setImageBitmap(incidentMapImage)
            etBriefingSituationSummary.setText(array.incidentSituation)
            etBriefingObjectives.setText(array.incidentObjectives)
        } else {
            tIncidentID3.text = "UU"
            tIncidentName3.text = getString(R.string.error_out_bounds)
            //tIncidentStart3.text = getString(R.string.error_out_bounds)
            val defaultMapImage =
                ContextCompat.getDrawable(this, R.drawable.incident_map_image_default)
            ivIncidentMapImage.setImageDrawable(defaultMapImage)
        }
    }

    fun setOrgChartText() {
        val arrayList = dbh.getSelectedOrgForChart(incidentID)
        if (arrayList.isNotEmpty()) {
            orgChartMap.forEach {
                fillOrgChartFromArrayList(arrayList,it.key,it.value)
            }
        }
    }

    fun saveOrgChartFields(){
        orgChartMap.forEach {
            val textView = it.key
            if (textView.text.isNotEmpty()) {
                val orgEntry = OrgList(
                    -1,
                    it.value,
                    "Command Staff",
                    textView.text.toString(),
                    incidentID
                )
                val persEntry = PersMinList(
                    -1,
                    textView.text.toString(),
                    it.value,
                    incidentID
                )
                dbh.insertOrgPosition(orgEntry)
                dbh.insertPersonBasic(persEntry)
            }
        }
    }


    fun fillOrgChartFromArrayList(arrayList: ArrayList<OrgChartList>, textView: AutoCompleteTextView,orgPos: String) {
        val searchResult = arrayList.firstOrNull { it.orgPosition == orgPos }
        searchResult?.let {
            Log.i("searchResult","${textView.toString()} search result: ${searchResult.toString()}")
            textView.setText(searchResult.orgPersName)
        }
    }

}