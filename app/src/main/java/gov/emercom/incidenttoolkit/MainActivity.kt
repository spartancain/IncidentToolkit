package gov.emercom.incidenttoolkit

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
    private var incidentID: Int = -1
    private lateinit var ptIncidentName: TextView
    private lateinit var acIncidentType: AutoCompleteTextView
    private lateinit var acIncidentLoc: AutoCompleteTextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: DatabaseHelper
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


        //Autofill Hints?? Not working yet but it aint broke the fucker so it stays
        val databaseHelper = DatabaseHelper(this@MainActivity)
        val incidentTypes = databaseHelper.getIncidentTypes().toString()
        acIncidentType.setAutofillHints(incidentTypes)

        //Recycler definitions
        db = DatabaseHelper(this)
        dbh = DatabaseHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displayIncident()
        //initialize newArray for onclicklistener in recyclerview
        newArray = arrayListOf<IncidentList>()


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
                if (selectedIncident != -1){
                    dbh.deleteIncident(selectedIncident)
                    Toast.makeText(this@MainActivity,"Incident $selectedIncident Deleted",Toast.LENGTH_SHORT).show()

                    ptIncidentName.text = ""
                    acIncidentType.setText("")
                    acIncidentLoc.setText("")
                    selectedIncident = -1
                    return displayIncident()
                }
                else {
                    Toast.makeText(this@MainActivity,"No Selection To Delete",Toast.LENGTH_SHORT).show()
                }
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
        adapter.setOnItemClickListener(object: MyAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity,"Clicked on $position",Toast.LENGTH_SHORT).show()
                Log.i("Adapter","Clicked $position")
            }

        })

        //longclick
        adapter.setOnItemLongClickListener(object: MyAdapter.OnItemLongClickListener{
            override fun onItemLongClick(position: Int, selectedIncidentID: String) {

                selectedIncident = selectedIncidentID.toInt()
                Toast.makeText(this@MainActivity,"Selected Incident $selectedIncidentID",Toast.LENGTH_SHORT).show()

                //Alert Dialog Box
//                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
//                dialogBuilder.setTitle("Long Click Received")
//                dialogBuilder.setMessage("Position $position Long-Clicked")
//                dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
//                    Toast.makeText(this@MainActivity,"OK Confirmed",Toast.LENGTH_SHORT).show()
//                })
//                dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
//                    dialog.dismiss()
//                })
//                    .show()
            }
        })

    }


}