package gov.emercom.incidenttoolkit

import android.R.id
import android.content.ContentValues.TAG
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    //references to layout controls (member variables)
    private lateinit var bSubmitIncident: Button
    private lateinit var bRefreshIncidents: Button
    private lateinit var bDeleteRecord: Button
    private lateinit var incidentID: id
    private lateinit var ptIncidentName: TextView
    private lateinit var acIncidentType: AutoCompleteTextView
    private lateinit var acIncidentLoc: AutoCompleteTextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: DatabaseHelper
    lateinit var dbh: DatabaseHelper
    private lateinit var newArray: ArrayList<IncidentList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bSubmitIncident = findViewById(R.id.bSubmitIncident)
        bRefreshIncidents = findViewById(R.id.bRefreshIncidents)
        bDeleteRecord = findViewById(R.id.bDeleteIncident)
        ptIncidentName = findViewById(R.id.ptIncidentName)
        acIncidentType = findViewById(R.id.acIncidentType)
        acIncidentLoc = findViewById(R.id.acIncidentLoc)
        recyclerView = findViewById(R.id.rvIncidentList)

        //Recycler definitions
        db = DatabaseHelper(this)
        dbh = DatabaseHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displayIncident()

        //foxandroid tutorial
        newArray = arrayListOf<IncidentList>()


        //Button listeners
        bSubmitIncident.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                // Action to perform when the button is clicked
                var incidentID = -1
                var incidentName = ptIncidentName.text.toString()
                var incidentType = acIncidentType.text.toString()
                var incidentLoc = acIncidentLoc.text.toString()
                var incidentStart = -1
                var incidentStartDTG = -1
                val incident = IncidentList(
                    incidentID,
                    incidentName,
                    incidentType,
                    incidentLoc,
                    incidentStart.toLong(),
                    incidentStartDTG.toString()
                )


                try {
                    val newIncident = IncidentList(
                        -1,
                        ptIncidentName.text.toString(),
                        acIncidentType.text.toString(),
                        acIncidentLoc.text.toString(),
                        incidentStart.toLong(),
                        incidentStartDTG.toString()
                    )
                    newIncident
                    ptIncidentName.text = ""
                    acIncidentType.setText("")
                    acIncidentLoc.setText("")
                }

                catch (e: Exception) {
                    val newIncident = IncidentList(
                        -1,
                        "Error",
                        "No Type",
                        "No Loc",
                        -1,
                        (-1).toString()
                    )
                    newIncident

                    val toast = Toast.makeText(
                        this@MainActivity,
                        "Error Submitting Incident",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    incident
                }

                val databaseHelper = DatabaseHelper(this@MainActivity)

                val success: Boolean = databaseHelper.insertIncident(incident)
                val toast = Toast.makeText(this@MainActivity, "Incident Creation $success",Toast.LENGTH_SHORT)
                toast.show()

                displayIncident()
            }
        })

        bRefreshIncidents.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                ptIncidentName.text = ""
                acIncidentType.setText("")
                acIncidentLoc.setText("")
                return displayIncident()


            }
        })



        bDeleteRecord.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }


        })

        recyclerView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
            return true}
        })

    }

    //Incident display updater for RecyclerView
    fun displayIncident() {
        var newCursor: Cursor? = dbh.getIncidentList()
        newArray = ArrayList<IncidentList>()
        while (newCursor!!.moveToNext()){
            val uIncidentID = newCursor.getInt(0)
            val uIncidentName = newCursor.getString(1)
            val uIncidentType = newCursor.getString(2)
            val uIncidentLoc = newCursor.getString(3)
            val uIncidentStart = newCursor.getLong(4)
            val uIncidentStartDTG = FormatDate(uIncidentStart)

            newArray.add(IncidentList(
                uIncidentID,
                uIncidentName,
                uIncidentType,
                uIncidentLoc,
                uIncidentStart,
                uIncidentStartDTG
            ))
            //var arrayText = newArray.toString()
            //Log.i("displayIncident",arrayText)
        }

        //changed in Foxandroid tutorial
        var adapter = MyAdapter(newArray)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity,"Clicked on $position",Toast.LENGTH_SHORT).show()
                Log.i("Adapter","Clicked $position")
            }

        })

    }


}