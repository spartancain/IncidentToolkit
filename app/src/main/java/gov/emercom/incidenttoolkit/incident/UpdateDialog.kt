package gov.emercom.incidenttoolkit.incident

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.incident.IncidentActivity
import gov.emercom.incidenttoolkit.main.MainActivity
import gov.emercom.incidenttoolkit.ui.theme.IncidentToolkitTheme

class UpdateDialog(
    context: Context,
    val updateTitle: String,
    val targetColumn: String,
    val targetValue: String,
    val keyColumn: String,
    val keyValue: String,
    dbh: DatabaseHelper
): Dialog(context){
    private lateinit var dialogTitle: TextView
    private lateinit var bPositive: Button
    private lateinit var bNegative: Button
    private lateinit var etUpdate: EditText
    lateinit var dbh: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_dialog)

        dbh = DatabaseHelper(context)
        dialogTitle = findViewById(R.id.tvDialogTitle)
        etUpdate = findViewById(R.id.etUpdateField)
        bNegative = findViewById(R.id.bNegative)
        bPositive = findViewById(R.id.bPositive)

        dialogTitle.text = updateTitle
        etUpdate.setText(targetValue)

        bPositive.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dbh.updateIncidentField(
                    keyColumn = keyColumn,
                    keyValue = keyValue,
                    targetColumn = targetColumn,
                    targetValue = etUpdate.text.toString()
                )
                Toast.makeText(v?.context, "Updated", Toast.LENGTH_SHORT).show()
                (ownerActivity as? IncidentActivity)?.onDialogClosed()
                dismiss()
            }
        })

        bNegative.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(v?.context, "Cancelled", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })
    }

}