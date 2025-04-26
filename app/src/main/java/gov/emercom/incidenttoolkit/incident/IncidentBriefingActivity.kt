package gov.emercom.incidenttoolkit.incident

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
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


class IncidentBriefingActivity: AppCompatActivity() {

    private lateinit var tIncidentName3: TextView
    private lateinit var tIncidentID3: TextView
    private lateinit var ivCloseBriefing: ImageView
    private lateinit var ivIncidentMapImage: ImageView
    private lateinit var bSelectMapImage: Button
    private lateinit var tIncidentStart3: TextView
    private var incidentID: Int = -1

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

    fun setTextFromArrayList(arrayList: ArrayList<IncidentGetList>) {
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
        } else {
            tIncidentID3.text = "UU"
            tIncidentName3.text = getString(R.string.error_out_bounds)
            //tIncidentStart3.text = getString(R.string.error_out_bounds)
            val defaultMapImage =
                ContextCompat.getDrawable(this, R.drawable.incident_map_image_default)
            ivIncidentMapImage.setImageDrawable(defaultMapImage)
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
        tIncidentStart3 = findViewById(R.id.tIncidentStart3)
        ivCloseBriefing = findViewById(R.id.ivCloseBriefing)
        ivIncidentMapImage = findViewById(R.id.ivIncidentMapImage)
        bSelectMapImage = findViewById(R.id.bSelectMapImage)

        setTextFromArrayList(incidentRow)

        bSelectMapImage.setOnClickListener (object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pickImageLauncher.launch(intent)
            }
        })


        //Hide the AppCompatActivity top action bar
        supportActionBar?.hide()
    }

}