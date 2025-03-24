package gov.emercom.incidenttoolkit

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MyAdapter (val incidentList: ArrayList<IncidentList>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //foxandroid tutorial
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }



    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tIncidentID: TextView? = itemView.findViewById(R.id.tIncidentID)
        val tIncidentName: TextView? = itemView.findViewById(R.id.tIncidentName)
        val tIncidentType: TextView? = itemView.findViewById(R.id.tIncidentType)
        val tIncidentLoc: TextView? = itemView.findViewById(R.id.tIncidentLoc)
        val tIncidentStart: TextView = itemView.findViewById(R.id.tIncidentStart)

        //foxandroid tutorial
        init {
            itemView.setOnClickListener {
                listener.onItemClick((absoluteAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(itemView,mListener)

    }

    override fun getItemCount(): Int {
        incidentList.size
        //Log.i("Adapter","$listSize")
        return incidentList.size
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentIncident = incidentList[position]
        holder.tIncidentID?.text = currentIncident.incidentID.toString()
        holder.tIncidentName?.text = currentIncident.incidentName
        holder.tIncidentType?.text = currentIncident.incidentType
        holder.tIncidentLoc?.text = currentIncident.incidentLoc
        holder.tIncidentStart.text = currentIncident.incidentStartDTG.toString()

    }

}