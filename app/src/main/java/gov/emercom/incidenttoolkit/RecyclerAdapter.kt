package gov.emercom.incidenttoolkit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class MyAdapter (val incidentList: ArrayList<IncidentList>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
        fun onItemClick(position: Int)
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
    class MyViewHolder(
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
                clickListener.onItemClick((absoluteAdapterPosition))
            }

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClick(absoluteAdapterPosition, selectedIncidentID = this.tIncidentID?.text.toString())
                Log.i("isSelected", "Set Background resource $selectedIncident")
                val selectedItem = selectedIncident
                val isSelected = selectedItem == bindingAdapterPosition
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
        ): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(itemView,clickListener, longClickListener, selectedIncident)

    }

    override fun getItemCount(): Int {
        incidentList.size
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

        val itemView = holder.itemView

        val selectedItem = selectedIncident.toString().toInt()



//        itemView.setOnLongClickListener {
//
//            longClickListener.onItemLongClick(position, holder.tIncidentID?.text.toString())
//            val selectedIncidentID = this.toString()
//            holder.itemView.setBackgroundResource(R.color.obj_blue_selected)
//            //Log.i("LongClicker","Position $position, Incident $selectedIncidentID")
//            return@setOnLongClickListener true
//        }
        selectedIncident = holder.absoluteAdapterPosition
        val isSelected = selectedItem == position
        if (isSelected) {
            holder.itemView.setBackgroundResource(R.color.obj_blue_selected)
        } else {
            holder.itemView.setBackgroundResource(R.color.obj_blue)
        }

    }

}