package gov.emercom.incidenttoolkit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (var incidentList: ArrayList<IncidentList>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

   // fun updateIncidents(newIncidentList: ArrayList<IncidentList>) {
        //incidentList.clear()
        //incidentList.addAll(newIncidentList)
//notifyItemInserted(0)
   // }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tIncidentID: TextView? = itemView.findViewById(R.id.tIncidentID)
        val tIncidentName: TextView? = itemView.findViewById(R.id.tIncidentName)
        val tIncidentType: TextView? = itemView.findViewById(R.id.tIncidentType)
        val tIncidentLoc: TextView? = itemView.findViewById(R.id.tIncidentLoc)
        val tIncidentStart: TextView = itemView.findViewById(R.id.tIncidentStart)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        var listSize = incidentList.size
        //Log.i("Adapter","$listSize")
        return incidentList.size
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentItem = incidentList[position]
        holder.tIncidentID?.text = currentItem.incidentID.toString()
        holder.tIncidentName?.text = currentItem.incidentName
        holder.tIncidentType?.text = currentItem.incidentType
        holder.tIncidentLoc?.text = currentItem.incidentLoc
        holder.tIncidentStart.text = currentItem.incidentStartDTG.toString()
    }


}