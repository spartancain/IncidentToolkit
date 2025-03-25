package gov.emercom.incidenttoolkit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (val incidentList: ArrayList<IncidentList>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //foxandroid tutorial
    private lateinit var clickListener : onItemClickListener

    //attempting longclick
    private lateinit var longClickListener : onItemLongClickListener

    //foxandroid click
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    //longclick
    interface onItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    //foxandroid
    fun setOnItemClickListener(listener: onItemClickListener){
        clickListener = listener
    }

    //longclick
    fun setOnItemLongClickListener(listener: onItemLongClickListener){
        longClickListener = listener
    }



    class MyViewHolder(itemView: View, clickListener: onItemClickListener, longClickListener: onItemLongClickListener): RecyclerView.ViewHolder(itemView){
        val tIncidentID: TextView? = itemView.findViewById(R.id.tIncidentID)
        val tIncidentName: TextView? = itemView.findViewById(R.id.tIncidentName)
        val tIncidentType: TextView? = itemView.findViewById(R.id.tIncidentType)
        val tIncidentLoc: TextView? = itemView.findViewById(R.id.tIncidentLoc)
        val tIncidentStart: TextView = itemView.findViewById(R.id.tIncidentStart)

        //foxandroid tutorial
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick((absoluteAdapterPosition))
            }

            //longclick
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClick((absoluteAdapterPosition))
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(itemView,clickListener, longClickListener)

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