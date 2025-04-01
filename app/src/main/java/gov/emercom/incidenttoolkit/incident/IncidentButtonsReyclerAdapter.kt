package gov.emercom.incidenttoolkit.incident

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.R

class IncidentButtonsReyclerAdapter (val buttonList: List<IncidentButton>): RecyclerView.Adapter<IncidentButtonsReyclerAdapter.ButtonViewHolder>() {

    class ButtonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.tvCardButtonName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.incident_buttons_cardview, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ButtonViewHolder,
        position: Int
    ) {
        val buttonText = buttonList[position].buttonName
        holder.button.text = buttonText
        holder.button.setOnClickListener {
            TODO()
        }
    }

    override fun getItemCount(): Int {
        return buttonList.size
    }




}