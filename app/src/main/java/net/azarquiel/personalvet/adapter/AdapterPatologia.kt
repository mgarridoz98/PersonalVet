package net.azarquiel.personalvet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.patologia_row.view.*
import net.azarquiel.personalvet.model.Foro
import net.azarquiel.personalvet.model.Patologia

/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterPatologia(val context: Context,
                       val layout: Int
                    ) : RecyclerView.Adapter<AdapterPatologia.ViewHolder>() {

    private var dataList: List<Patologia> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setPatologia(patologia: List<Patologia>) {
        this.dataList = patologia
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Patologia){
            itemView.tvpatologiarow.text = dataItem.nombre
            itemView.tag = dataItem
        }

    }
}