package gov.emercom.incidenttoolkit.incident

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.incident.IncidentBriefingActivity
import gov.emercom.incidenttoolkit.main.IncidentGetList

class IncidentObjectivesActivity: AppCompatActivity() {

    private var incidentID: Int = -1
    private lateinit var tIncidentID4: TextView
    private lateinit var tIncidentName4: TextView
    private lateinit var tIncidentStart4: TextView
    private lateinit var etObjectivesObjectives: AutoCompleteTextView
    private lateinit var etObjectivesEmphasis: AutoCompleteTextView
    private lateinit var etObjectivesSA: AutoCompleteTextView
    private lateinit var swObjectivesSafetyRequred: SwitchMaterial
    private lateinit var etObjectivesSafetyLoc: AutoCompleteTextView
    private lateinit var bObjectivesSaveRemain: Button
    private lateinit var bObjectivesSaveExit: Button

    val dbh = DatabaseHelper(this@IncidentObjectivesActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_incident_objectives)
        supportActionBar?.hide()

        incidentID = intent.getIntExtra("selectedIncidentID",-1)
        val incidentRow = dbh.getSelectedIncident(incidentID)

        tIncidentID4 = findViewById(R.id.tIncidentID4)
        tIncidentName4 = findViewById(R.id.tIncidentName4)
        tIncidentStart4 = findViewById(R.id.tIncidentStart4)
        etObjectivesObjectives = findViewById(R.id.etObjectivesObjectives)
        etObjectivesEmphasis = findViewById(R.id.etObjectivesEmphasis)
        etObjectivesSA = findViewById(R.id.etObjectivesSA)
        swObjectivesSafetyRequred = findViewById(R.id.swObjectivesSafetyRequired)
        etObjectivesSafetyLoc = findViewById(R.id.etObjectivesSafetyLoc)
        bObjectivesSaveRemain = findViewById(R.id.bObjectivesSaveRemain)
        bObjectivesSaveExit = findViewById(R.id.bObjectivesSaveExit)

        setIncidentArrayText(incidentRow)

        bObjectivesSaveRemain.setOnClickListener {

            if (etObjectivesObjectives.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_OBJS,
                    etObjectivesObjectives.text.toString()
                )
            }

            if (etObjectivesEmphasis.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_EMPHASIS,
                    etObjectivesEmphasis.text.toString()
                )
            }

            if (etObjectivesSA.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SA,
                    etObjectivesSA.text.toString()
                )
            }

            dbh.updateSwitchState(
                dbh.INCIDENT_TABLE,
                dbh.COL_ID,
                incidentID.toString(),
                dbh.COL_SAFETYPLANREQ,
                swObjectivesSafetyRequred.isChecked
            )

            if (etObjectivesSafetyLoc.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SAFETYPLANLOC,
                    etObjectivesSafetyLoc.text.toString()
                )
            }

            Toast.makeText(this@IncidentObjectivesActivity, "Incident Objectives Saved.", Toast.LENGTH_SHORT).show()
        }

        bObjectivesSaveExit.setOnClickListener {

            if (etObjectivesObjectives.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_OBJS,
                    etObjectivesObjectives.text.toString()
                )
            }

            if (etObjectivesEmphasis.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_EMPHASIS,
                    etObjectivesEmphasis.text.toString()
                )
            }

            if (etObjectivesSA.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SA,
                    etObjectivesSA.text.toString()
                )
            }

            dbh.updateSwitchState(
                dbh.INCIDENT_TABLE,
                dbh.COL_ID,
                incidentID.toString(),
                dbh.COL_SAFETYPLANREQ,
                swObjectivesSafetyRequred.isChecked
            )

            if (etObjectivesSafetyLoc.text.isNotEmpty()) {
                dbh.updateField(
                    dbh.INCIDENT_TABLE,
                    dbh.COL_ID,
                    incidentID.toString(),
                    dbh.COL_SAFETYPLANLOC,
                    etObjectivesSafetyLoc.text.toString()
                )
            }

            Toast.makeText(this@IncidentObjectivesActivity, "Incident Objectives Saved.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun setIncidentArrayText(arrayList: ArrayList<IncidentGetList>) {
        if (arrayList.isNotEmpty()) {
            val array = arrayList[0]
            val incidentName = array.incidentName
            val incidentStart = array.incidentStartDTG
            tIncidentID4.text = incidentID.toString()
            tIncidentName4.text = incidentName
            tIncidentStart4.text = incidentStart
            etObjectivesObjectives.setText(array.incidentObjectives)
            etObjectivesEmphasis.setText(array.incidentEmphasis)
            etObjectivesSA.setText(array.incidentSA)
            swObjectivesSafetyRequred.isChecked = array.incidentSafetyPlanReq == 1
            etObjectivesSafetyLoc.setText(array.incidentSafetyPlanLoc)
        } else {
            tIncidentID4.text = "UU"
            tIncidentName4.text = getString(R.string.error_out_bounds)
            tIncidentStart4.text = getString(R.string.error_out_bounds)
        }
    }


}