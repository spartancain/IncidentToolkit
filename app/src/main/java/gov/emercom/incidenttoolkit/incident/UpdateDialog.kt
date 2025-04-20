package gov.emercom.incidenttoolkit.incident

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.R

class UpdateDialog(
    context: Context,
    val updateTitle: String,
    val targetColumn: String,
    val targetValue: String,
    val keyColumn: String,
    val keyValue: String,
    private val listener: DialogListener
): DialogFragment(){
    private lateinit var dialogTitle: TextView
    private lateinit var bPositive: Button
    private lateinit var bNegative: Button
    private lateinit var etUpdate: EditText
    lateinit var dbh: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_dialog,container,false )

        dbh = DatabaseHelper(requireContext())
        dialogTitle = view.findViewById(R.id.tvDialogTitle)
        etUpdate = view.findViewById(R.id.etUpdateField)
        bNegative = view.findViewById(R.id.bNegative)
        bPositive = view.findViewById(R.id.bPositive)


        dialogTitle.text = updateTitle
        etUpdate.setText(targetValue)

        bPositive.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val updateValue = etUpdate.text.toString()
                if (updateValue.length > 2) {
                    dbh.updateIncidentField(
                        keyColumn = keyColumn,
                        keyValue = keyValue,
                        targetColumn = targetColumn,
                        targetValue = updateValue
                    )
                    Toast.makeText(v?.context, "Updated", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    Toast.makeText(v?.context,"Value too short!",Toast.LENGTH_SHORT).show()
                }
            }
        })

        bNegative.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(v?.context, "Cancelled", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })

        return view
    }

    override fun onDismiss(dialog: DialogInterface){
        super.onDismiss(dialog)
        listener.onDialogClose()
    }
}