package gov.emercom.incidenttoolkit.incident

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.DatabaseHelper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import gov.emercom.incidenttoolkit.main.IncidentList
import gov.emercom.incidenttoolkit.main.MainActivity

class IncidentActivity : ComponentActivity() {

    private lateinit var tIncidentName2: TextView
    private lateinit var tIncidentLoc2: TextView
    private lateinit var tIncidentID2: TextView
    private lateinit var tIncidentStart2: TextView
    private lateinit var ivEditName: ImageView
    private lateinit var ivEditLoc: ImageView
    private lateinit var ivCloseIncident: ImageView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_incident)

        //Get selected incident ID from MainActivity and produce incident info array
        val databaseHelper = DatabaseHelper(this@IncidentActivity)
        val selectedIncidentID: Int = intent.getStringExtra("selectedIncidentID")!!.toInt()
        val incidentRow = databaseHelper.getSelectedIncident(selectedIncidentID)
        Log.i("IncidentActivityDB","$incidentRow")

        //tie vars to layout items
        tIncidentName2 = findViewById(R.id.tIncidentName2)
        tIncidentLoc2 = findViewById(R.id.tIncidentLoc2)
        tIncidentID2 = findViewById(R.id.tIncidentID2)
        tIncidentStart2 = findViewById(R.id.tIncidentStart2)
        ivEditName = findViewById(R.id.ivEditName)
        ivEditLoc = findViewById(R.id.ivEditLoc)
        ivCloseIncident = findViewById(R.id.ivCloseIncident)
        recyclerView = findViewById(R.id.rvIncidentButtons)

        //apply incident info array to textview fields on layout
        fun setTextFromArrayList(arrayList: ArrayList<IncidentList>) {
            if (arrayList.isNotEmpty() ){
                val array = arrayList[0]
                val incidentID = array.incidentID
                val incidentName = array.incidentName
                val incidentLoc = array.incidentLoc
                val incidentType = array.incidentType
                val incidentStart = array.incidentStartDTG
                tIncidentID2.text = incidentID.toString()
                tIncidentName2.text = incidentName
                tIncidentLoc2.text = incidentLoc
                tIncidentStart2.text = incidentStart
            } else {
                tIncidentID2.text = "UU"
                tIncidentName2.text = getString(R.string.error_out_bounds)
                tIncidentLoc2.text = getString(R.string.error_out_bounds)
                tIncidentStart2.text = getString(R.string.error_out_bounds)
            }
        }
        setTextFromArrayList(incidentRow)

        //close incident button
        ivCloseIncident.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogBuilder = AlertDialog.Builder(this@IncidentActivity)
                dialogBuilder.setTitle("Exit Incident")
                dialogBuilder.setMessage("Return to Incident List")
                dialogBuilder.setPositiveButton("Exit") { dialog, which ->
                    val intent = Intent(ivCloseIncident.context, MainActivity::class.java)
                    ivCloseIncident.context.startActivity(intent)
                }
                dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(this@IncidentActivity, "Cancelled", Toast.LENGTH_SHORT).show()
                }
                    .show()
            }

        })

        val buttonList = listOf(
            IncidentButton("Briefing","Incident Briefing\n(ICS 201)"),
            IncidentButton("Objectives","Incident Objectives\n(ICS 202)"),
            IncidentButton("OrgList","Organization Assignment List\n(ICS 203)"),
            IncidentButton("AssignmentList","Assignment List\n(ICS 204)"),
            IncidentButton("RadioPlan","Radio Communications Plan\n(ICS 205)"),
            IncidentButton("CommList","Communications List\n(ICS 205A)"),
            IncidentButton("MedicalPlan","Medical Plan\n(ICS 206)"),
            IncidentButton("OrgChart","Organization Chart\n(ICS 207)"),
            IncidentButton("SafetyPlan","Safety Plan\n(ICS 208)"),
            IncidentButton("StatusSummary","Status Summary\n(ICS 209)"),
            IncidentButton("ResourceStatus","Resource Status Change\n(ICS 210)"),
            IncidentButton("CheckInList","Check-In List\n(ICS 211)"),
            IncidentButton("Message","General Message\n(ICS 213)"),
            IncidentButton("ResourceRequest","Resource Request Message\n(ICS 213RR)"),
            IncidentButton("ActivityLog","Activity Log\n(ICS 214)"),
            IncidentButton("CommResources","Comm Resource Worksheet\n(ICS 217A)")
        )
        recyclerView.layoutManager = GridLayoutManager(this,2)
        val adapter = IncidentButtonsReyclerAdapter(buttonList)
        recyclerView.adapter = adapter
    }

}