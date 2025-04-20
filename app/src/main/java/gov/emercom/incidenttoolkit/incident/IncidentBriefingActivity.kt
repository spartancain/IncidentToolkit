package gov.emercom.incidenttoolkit.incident

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.incident.IncidentActivity
import gov.emercom.incidenttoolkit.main.IncidentList



class IncidentBriefingActivity: AppCompatActivity() {

    private lateinit var tIncidentName3: TextView
    private lateinit var tIncidentID3: TextView
//    private lateinit var tIncidentStart3: TextView

    fun setTextFromArrayList(arrayList: ArrayList<IncidentList>) {
        if (arrayList.isNotEmpty() ){
            val array = arrayList[0]
            val incidentID = array.incidentID
            val incidentName = array.incidentName
            val incidentStart = array.incidentStartDTG
            tIncidentID3.text = incidentID.toString()
            tIncidentName3.text = incidentName
            //tIncidentStart3.text = incidentStart
        } else {
            tIncidentID3.text = "UU"
            tIncidentName3.text = getString(R.string.error_out_bounds)
            //tIncidentStart3.text = getString(R.string.error_out_bounds)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_incident_briefing)

        //Get selected incident ID from MainActivity and produce incident info array
        val dbh = DatabaseHelper(this@IncidentBriefingActivity)
        val selectedIncidentID: Int = intent.getIntExtra("selectedIncidentID",-1)
        val incidentRow = dbh.getSelectedIncident(selectedIncidentID)

        tIncidentName3 = findViewById(R.id.tIncidentName3)
        tIncidentID3 = findViewById(R.id.tIncidentID3)

        setTextFromArrayList(incidentRow)



        //Hide the AppCompatActivity top action bar
        supportActionBar?.hide()
    }
}