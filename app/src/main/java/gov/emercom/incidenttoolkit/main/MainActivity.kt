package gov.emercom.incidenttoolkit.main

import android.app.AlertDialog
import android.content.DialogInterface
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
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.FormatDate
import gov.emercom.incidenttoolkit.R

class MainActivity : ComponentActivity() {

    //references to layout controls (member variables)
    private lateinit var bSubmitIncident: Button
    private lateinit var bRefreshIncidents: Button
    private lateinit var bDeleteRecord: Button
    private lateinit var ptIncidentName: TextView
    private lateinit var acIncidentType: AutoCompleteTextView
    private lateinit var acIncidentLoc: AutoCompleteTextView
    private lateinit var recyclerView: RecyclerView
    lateinit var dbh: DatabaseHelper
    private lateinit var newArray: ArrayList<IncidentList>
    private var selectedIncident: Int = -1

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
        dbh = DatabaseHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        //initialize newArray for onclicklistener in recyclerview
        newArray = arrayListOf<IncidentList>()


        //Autofill Hints?? Not working yet but it aint broke the fucker so it stays
        val incidentTypes = dbh.getIncidentTypes().toString()
        acIncidentType.setAutofillHints(incidentTypes)


        //Button listeners
        bSubmitIncident.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                // Action to perform when the button is clicked
                val incidentID = -1
                val incidentName = ptIncidentName.text.toString()
                val incidentType = acIncidentType.text.toString()
                val incidentLoc = acIncidentLoc.text.toString()
                val incidentStart = -1
                val incidentStartDTG = -1
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


                    val toast = Toast.makeText(
                        this@MainActivity,
                        "Error Submitting Incident",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
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
                if (selectedIncident != -1){
                    //Alert Dialog Box
                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder.setTitle("Confirm Incident Deletion")
                dialogBuilder.setMessage("Delete Incident $selectedIncident?")
                dialogBuilder.setPositiveButton("Delete") { dialog, which ->
                    dbh.deleteIncident(selectedIncident)
                    Toast.makeText(
                        this@MainActivity,
                        "Incident $selectedIncident Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    ptIncidentName.text = ""
                    acIncidentType.setText("")
                    acIncidentLoc.setText("")
                    selectedIncident = -1
                    displayIncident()
                }
                    dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                        ptIncidentName.text = ""
                        acIncidentType.setText("")
                        acIncidentLoc.setText("")
                        selectedIncident = -1
                        displayIncident()
                        Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
                    }
                        .show()
                }
                else {
                    Toast.makeText(this@MainActivity,"No Selection To Delete",Toast.LENGTH_SHORT).show()
                }
            }


        })
        displayIncident()
    }

    //Incident display updater for RecyclerView

    fun displayIncident() {
        val newCursor: Cursor = dbh.getIncidentList()
        newArray = ArrayList<IncidentList>()
        while (newCursor.moveToNext()){
            val uIncidentID = newCursor.getInt(0)
            val uIncidentName = newCursor.getString(1)
            val uIncidentType = newCursor.getString(2)
            val uIncidentLoc = newCursor.getString(3)
            val uIncidentStart = newCursor.getLong(4)
            val uIncidentStartDTG = FormatDate(uIncidentStart)

            newArray.add(
                IncidentList(
                uIncidentID,
                uIncidentName,
                uIncidentType,
                uIncidentLoc,
                uIncidentStart,
                uIncidentStartDTG
            )
            )
            //var arrayText = newArray.toString()
            //Log.i("displayIncident",arrayText)
        }

        //changed in Foxandroid tutorial
        val adapter = IncidentsRecyclerAdapter(newArray)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: IncidentsRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, selectedIncidentID: String) {
//                Toast.makeText(this@MainActivity,"Clicked on $position",Toast.LENGTH_SHORT).show()
//                Log.i("Adapter","Clicked $position")
            }

        })

        //longclick
        adapter.setOnItemLongClickListener(object: IncidentsRecyclerAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int, selectedIncidentID: String) {

                selectedIncident = selectedIncidentID.toInt()
                IncidentsRecyclerAdapter.OnSelectedIncident.selectedIncident = selectedIncident

                Toast.makeText(this@MainActivity,"Selected Incident $selectedIncidentID",Toast.LENGTH_SHORT).show()

            }
        })

    }


}