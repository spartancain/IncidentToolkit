package gov.emercom.incidenttoolkit.incident

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.DatabaseHelper
import gov.emercom.incidenttoolkit.main.TimelineList
import gov.emercom.incidenttoolkit.R

class IncidentActionsRecyclerAdapter(val timelineList: ArrayList<TimelineList>) :
    RecyclerView.Adapter<IncidentActionsRecyclerAdapter.IncidentActionsViewHolder>() {



    class IncidentActionsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val etActionTime: AutoCompleteTextView? = itemView.findViewById(R.id.etActionTime)
        val etActionAction: AutoCompleteTextView? = itemView.findViewById(R.id.etActionAction)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncidentActionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.incident_briefing_actions_cardview, parent, false)
        return IncidentActionsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: IncidentActionsViewHolder,
        position: Int
    ) {
        val currentAction = timelineList[position]
        holder.etActionTime?.setText(currentAction.timePeriodStart)
        holder.etActionAction?.setText(currentAction.timePeriodRef)

        holder.etActionTime?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentAction.timePeriodStart =
                    s.toString() //changing array contents to include changed text
                timelineList[holder.absoluteAdapterPosition] =
                    currentAction //updating timelineList backwards
                Log.i("etActionTime", timelineList[holder.absoluteAdapterPosition].toString())
            }
        })

        holder.etActionAction?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                currentAction.timePeriodRef =
                    s.toString() //changing array contents to include changed text
                timelineList[holder.absoluteAdapterPosition] =
                    currentAction //updating timelineList backwards
                Log.i("etActionAction", timelineList[holder.absoluteAdapterPosition].toString())
            }
        })

        holder.itemView
    }

    override fun getItemCount(): Int {
        timelineList.size
        return timelineList.size
    }

}