package gov.emercom.incidenttoolkit.incident

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
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

    //image picker
    private  var pickImageLauncher: ActivityResultLauncher<Intent> = registerForActivityResult (ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            imageUri?.let {
                val dbh = DatabaseHelper(this@IncidentBriefingActivity)
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

    fun setIncidentArrayText(arrayList: ArrayList<IncidentGetList>) {
        if (arrayList.isNotEmpty()) {
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
        if (arrayList.isNotEmpty()) {
            val arrayIndexIC = searchArrayList(arrayList, "Incident Commander`")
            val arrayIndexLiaison = searchArrayList(arrayList, "Liaison Officer`")
            val arrayIndexSafety = searchArrayList(arrayList, "Safety Officer`")
            val arrayIndexPIO = searchArrayList(arrayList, "Public Information Officer`")
            val arrayIndexOpsChief = searchArrayList(arrayList, "Operations Chief`")
            val arrayIndexPlanChief = searchArrayList(arrayList, "Planning Chief`")
            val arrayIndexLogsChief = searchArrayList(arrayList, "Logistics Chief`")
            val arrayIndexFinanceChief = searchArrayList(arrayList, "Finance Chief`")

            val arrayIC = arrayList[arrayIndexIC]
            val arrayLiaison = arrayList[arrayIndexLiaison]
            val arraySafety = arrayList[arrayIndexSafety]
            val arrayPIO = arrayList[arrayIndexPIO]
            val arrayOpsChief = arrayList[arrayIndexOpsChief]
            val arrayPlanChief = arrayList[arrayIndexPlanChief]
            val arrayLogsChief = arrayList[arrayIndexLogsChief]
            val arrayFinanceChief = arrayList[arrayIndexFinanceChief]

            val orgChartNameArray = arrayOf(
                arrayIC.persName, //Index 0
                arrayLiaison.persName, //Index 1
                arraySafety.persName, //Index 2
                arrayPIO.persName, //Index 3
                arrayOpsChief.persName, //Index 4
                arrayPlanChief.persName, //Index 5
                arrayLogsChief.persName, //Index 6
                arrayFinanceChief.persName //Index 7
            )

            for (value in orgChartNameArray) {
                if (isProperString(value)) {
                    etBriefingOrgIC.setText(orgChartNameArray[0])
                    etBriefingOrgLiaison.setText(orgChartNameArray[1])
                    etBriefingOrgSafety.setText(orgChartNameArray[2])
                    etBriefingOrgPIO.setText(orgChartNameArray[3])
                    etBriefingOrgOpsChief.setText(orgChartNameArray[4])
                    etBriefingOrgPlanChief.setText(orgChartNameArray[5])
                    etBriefingOrgLogsChief.setText(orgChartNameArray[6])
                    etBriefingOrgFinanceChief.setText(orgChartNameArray[7])
                }
            }

        }
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
        bBriefingSaveRemain = findViewById(R.id.bBriefingSaveRemain)
        bBriefingSaveExit = findViewById(R.id.bBriefingSaveExit)

        /*        etBriefingSituationSummary.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                    var oldText = etBriefingSituationSummary.text.toString()
                    if (!hasFocus) {
                        var resultArray: Array<Any> = arrayOf(etBriefingSituationSummary,"UU")
                        var newText = etBriefingSituationSummary.text.toString()
                        if (newText != oldText) {
                            resultArray = arrayOf(etBriefingSituationSummary, newText)
                        }
                        Log.i("compareOnFocusLoss", "Array Resource ${resultArray[0]}, Old Value $oldText, New Value ${resultArray[1]}")
                        changeArrayList.add(resultArray)
                        return@OnFocusChangeListener
                    }
                }*/

        setIncidentArrayText(incidentRow)
        setPersArrayText(incidentPersList)

        ivIncidentMapImage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
            return@setOnLongClickListener true
        }

        bBriefingSaveRemain.setOnClickListener {
            val briefingOrgICNow = etBriefingOrgIC.text.toString()
            val briefingOrgLiaisonNow = etBriefingOrgLiaison.text.toString()
            val briefingOrgSafetyNow = etBriefingOrgSafety.text.toString()
            val briefingOrgPIONow = etBriefingOrgPIO.text.toString()
            val briefingOrgOpsChiefNow = etBriefingOrgOpsChief.text.toString()
            val briefingOrgPlanChiefNow = etBriefingOrgPlanChief.text.toString()
            val briefingOrgLogsChiefNow = etBriefingOrgLogsChief.text.toString()
            val briefingOrgFinanceChiefNow = etBriefingOrgFinanceChief.text.toString()

            dbh.updateField(
                dbh.INCIDENT_TABLE,
                dbh.COL_ID,
                incidentID.toString(),
                dbh.COL_SITU,
                etBriefingSituationSummary.text.toString()
            )
            dbh.updateField(
                dbh.INCIDENT_TABLE,
                dbh.COL_ID,
                incidentID.toString(),
                dbh.COL_OBJS,
                etBriefingObjectives.text.toString()
            )

        }

        //Hide the AppCompatActivity top action bar
        supportActionBar?.hide()
    }

    fun <T> searchArrayList(list: ArrayList<T>, query: String): Int {
        for (index in list.indices) {
            if (list[index] == query) {
                return index // Return the index if found
            }
        }
        return -1 // Return -1 if not found
    }

    fun isProperString(value: Any): Boolean {
        return value is String
    }

    fun compareOnFocusLoss(
        autoCompleteTextView: AutoCompleteTextView,
        existingText: String
    ): Array<String> {
        var oldText = existingText
        var newText = "UU"
        autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                newText = (view as AutoCompleteTextView).text.toString()
                if (newText != oldText) {
                    oldText = newText
                    val resultArray =
                        arrayOf(resources.getResourceEntryName(autoCompleteTextView.id), newText)
                    Log.i(
                        "compareOnFocusLoss",
                        "Array Resource ${resultArray[0]}, New Value ${resultArray[1]}"
                    )
                    return@OnFocusChangeListener
                }
            }
        }
        return arrayOf("bees")
    }

}