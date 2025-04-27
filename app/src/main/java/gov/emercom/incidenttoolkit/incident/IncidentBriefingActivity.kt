package gov.emercom.incidenttoolkit.incident

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.main.IncidentGetList
import gov.emercom.incidenttoolkit.main.PersList

class IncidentBriefingActivity: AppCompatActivity() {

    private var incidentID: Int = -1
    private lateinit var tIncidentName3: TextView
    private lateinit var tIncidentID3: TextView
    private lateinit var ivCloseBriefing: ImageView
    private lateinit var tIncidentStart3: TextView
    private lateinit var ivIncidentMapImage: ImageView
    private lateinit var etBriefingSituationSummary: EditText
    private lateinit var etBriefingObjectives: EditText
    private lateinit var etBriefingOrgIC: EditText
    private lateinit var etBriefingOrgLiaison: EditText
    private lateinit var etBriefingOrgSafety: EditText
    private lateinit var etBriefingOrgPIO: EditText
    private lateinit var etBriefingOrgOpsChief: EditText
    private lateinit var etBriefingOrgPlanChief: EditText
    private lateinit var etBriefingOrgLogsChief: EditText
    private lateinit var etBriefingOrgFinanceChief: EditText


    //image picker
    private  var pickImageLauncher: ActivityResultLauncher<Intent> = registerForActivityResult (ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            imageUri?.let {
                val dbh = DatabaseHelper(this@IncidentBriefingActivity)
                val bitmapSource = ImageDecoder.createSource(contentResolver, it)
                val bitmap = ImageDecoder.decodeBitmap(bitmapSource)
                //ivIncidentMapImage.setImageBitmap(bitmap)
                dbh.updateIncidentMapImage(
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

    fun setIncidentArrayText(arrayList: ArrayList<IncidentGetList>) {
        if (arrayList.isNotEmpty() ){
            val array = arrayList[0]
            incidentID = array.incidentID
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

    fun setPersArrayText(arrayList: ArrayList<PersList>) {
        TODO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_incident_briefing)

        //Get selected incident ID from MainActivity and produce incident info arrays
        val dbh = DatabaseHelper(this@IncidentBriefingActivity)
        val selectedIncidentID: Int = intent.getIntExtra("selectedIncidentID",-1)
        val incidentRow = dbh.getSelectedIncident(selectedIncidentID)
        val incidentPersList = dbh.getSelectedPers("COLUMN_ID", selectedIncidentID.toString())

        tIncidentName3 = findViewById(R.id.tIncidentName3)
        tIncidentID3 = findViewById(R.id.tIncidentID3)
        tIncidentStart3 = findViewById(R.id.tIncidentStart3)
        ivCloseBriefing = findViewById(R.id.ivCloseBriefing)
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

        setIncidentArrayText(incidentRow)

        ivIncidentMapImage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
            return@setOnLongClickListener true
        }

        //Hide the AppCompatActivity top action bar
        supportActionBar?.hide()
    }

}