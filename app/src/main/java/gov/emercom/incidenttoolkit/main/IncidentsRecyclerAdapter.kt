package gov.emercom.incidenttoolkit.main

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gov.emercom.incidenttoolkit.R
import gov.emercom.incidenttoolkit.incident.IncidentActivity

class IncidentsRecyclerAdapter(val incidentPutList: ArrayList<IncidentPutList>) :
    RecyclerView.Adapter<IncidentsRecyclerAdapter.IncidentListViewHolder>() {

    //Click and LongClick vars interfaces and funs
    private lateinit var clickListener : OnItemClickListener
    private lateinit var longClickListener : OnItemLongClickListener

    //highlighter attempt
    private var selectedIncident : Int = -1

    interface OnSelectedIncident{
        fun onSelectedIncident(position: Int)
        companion object{
            var selectedIncident: Int = -1
        }
    }
    //

    interface OnItemClickListener{
        fun onItemClick(position: Int, selectedIncidentID: String)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int, selectedIncidentID: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        clickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener){
        longClickListener = listener
    }


//Actual view holder
    class IncidentListViewHolder(
    itemView: View,
    clickListener: OnItemClickListener,
    longClickListener: OnItemLongClickListener,
    selectedIncident: Int
        ): RecyclerView.ViewHolder(itemView){

        val tIncidentID: TextView? = itemView.findViewById(R.id.tIncidentID)
        val tIncidentName: TextView? = itemView.findViewById(R.id.tIncidentName)
        val tIncidentType: TextView? = itemView.findViewById(R.id.tIncidentType)
        val tIncidentLoc: TextView? = itemView.findViewById(R.id.tIncidentLoc)
        val tIncidentStart: TextView = itemView.findViewById(R.id.tIncidentStart)


        //Recycler Item Click and LongClick listeners
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(absoluteAdapterPosition, selectedIncidentID = this.tIncidentID?.text.toString())
                val intent = Intent(itemView.context, IncidentActivity::class.java)
                val selectedIncidentID = this.tIncidentID?.text.toString()
                intent.putExtra("selectedIncidentID", selectedIncidentID)
                Log.i("ClickedIncident", "Incident $selectedIncidentID")
                itemView.context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClick(
                    absoluteAdapterPosition,
                    selectedIncidentID = this.tIncidentID?.text.toString()
                )
                Log.i("isSelected", "Set Background resource $selectedIncident")
                val isSelected = selectedIncident == bindingAdapterPosition
                if (isSelected) {
                    itemView.setBackgroundResource(R.color.obj_blue_selected)
                } else {
                    itemView.setBackgroundResource(R.color.obj_blue)
                }
                return@setOnLongClickListener true
            }

            //highlighter maybe?

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): IncidentListViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.incident_recycler_item,parent,false)
        return IncidentListViewHolder(itemView,clickListener, longClickListener, selectedIncident)

    }

    override fun getItemCount(): Int {
        incidentPutList.size
        return incidentPutList.size
    }

    override fun onBindViewHolder(
        holder: IncidentListViewHolder,
        position: Int
    ) {
        val currentIncident = incidentPutList[position]
        holder.tIncidentID?.text = currentIncident.incidentID.toString()
        holder.tIncidentName?.text = currentIncident.incidentName
        holder.tIncidentType?.text = currentIncident.incidentType
        holder.tIncidentLoc?.text = currentIncident.incidentLoc
        holder.tIncidentStart.text = currentIncident.incidentStartDTG

        holder.itemView

        selectedIncident.toString().toInt()

        selectedIncident = holder.absoluteAdapterPosition

    }

}