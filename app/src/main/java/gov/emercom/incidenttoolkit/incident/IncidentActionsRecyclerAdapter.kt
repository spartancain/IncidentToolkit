package gov.emercom.incidenttoolkit.incident

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.main.TimelineList
import gov.emercom.incidenttoolkit.R

class IncidentActionsRecyclerAdapter (val timelineList: ArrayList<TimelineList>) :
    RecyclerView.Adapter<IncidentActionsRecyclerAdapter.IncidentActionsViewHolder>() {

    class IncidentActionsViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val etActionTime: AutoCompleteTextView? = itemView.findViewById(R.id.etActionTime)
        val etActionAction: AutoCompleteTextView? = itemView.findViewById(R.id.etActionAction)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncidentActionsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.incident_briefing_actions_cardview, parent, false)
        return IncidentActionsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: IncidentActionsViewHolder,
        position: Int
    ) {
        val currentAction = timelineList[position]
        holder.etActionTime?.setText(currentAction.timePeriodStart)
        holder.etActionAction?.setText(currentAction.timePeriodRef)

        holder.itemView
    }

    override fun getItemCount(): Int {
        timelineList.size
        return timelineList.size
    }
}