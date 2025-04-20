package gov.emercom.incidenttoolkit.incident

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.incident.IncidentActivity
import kotlin.math.abs

class IncidentButtonsReyclerAdapter (val buttonList: List<IncidentButton>, val activityIncidentID: Int): RecyclerView.Adapter<IncidentButtonsReyclerAdapter.ButtonViewHolder>() {

    private lateinit var clickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int, selectedActivity: String)

    }

        fun setOnItemClickListener(listener: OnItemClickListener){
        clickListener = listener
    }


    class ButtonViewHolder(itemView: View, clickListener: OnItemClickListener,val buttonList: List<IncidentButton>, val currentIncidentID: Int): RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.tvCardButtonName)

        init {
            button.setOnClickListener {
                val selectedActivity = buttonList[absoluteAdapterPosition].activityName
                clickListener.onItemClick(absoluteAdapterPosition, selectedActivity)
                /*lateinit var intent: Intent
                //Log.i("buttonClick","position $position, activity $selectedActivity")
                try {
                    when (absoluteAdapterPosition) {
                        0 -> {intent = Intent(itemView.context, IncidentBriefingActivity::class.java)}
                        else -> {intent = Intent(itemView.context, selectedActivity::class.java)}
                    }
                    Log.i("intentClick",intent.toString())
//                    intent.putExtra("selectedIncidentID", currentIncidentID)
                    Log.i("intentExtra", intent.getStringExtra("incidentID").toString())
                    intent.getStringExtra("incidentID")?.let {
                        itemView.context.startActivity(intent)
                        TODO("fucking selectedIncidentID continues to return null value")
                    }
                } catch (e: ActivityNotFoundException){
                    Log.i("intentClick",intent.toString())
                    Toast.makeText(itemView.context, "Activity Not Found!", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }*/
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.incident_buttons_cardview, parent, false)
        val currentIncidentID = activityIncidentID
        return ButtonViewHolder(view, clickListener, buttonList, currentIncidentID)
    }

    override fun onBindViewHolder(
        holder: ButtonViewHolder,
        position: Int
    ) {
        val buttonText = buttonList[position].buttonName
        holder.button.text = buttonText
    }

    override fun getItemCount(): Int {
        return buttonList.size
    }




}