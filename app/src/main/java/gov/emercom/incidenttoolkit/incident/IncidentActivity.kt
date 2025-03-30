package gov.emercom.incidenttoolkit.incident

import android.app.AlertDialog
import android.content.DialogInterface
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

        ivCloseIncident.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogBuilder = AlertDialog.Builder(this@IncidentActivity)
                dialogBuilder.setTitle("Exit Incident")
                dialogBuilder.setMessage("Confirm Exit")
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
    }

}