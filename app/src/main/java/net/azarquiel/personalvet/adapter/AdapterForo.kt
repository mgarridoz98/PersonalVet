package net.azarquiel.personalvet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.personalvet.model.Foro

/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterForo(val context: Context,
                  val layout: Int
                    ) : RecyclerView.Adapter<AdapterForo.ViewHolder>() {

    private var dataList: List<Foro> = emptyList()

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

    internal fun setGastosView(gastosView: List<Foro>) {
        this.dataList = gastosView
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Foro){

            itemView.tag = dataItem
        }

    }
}