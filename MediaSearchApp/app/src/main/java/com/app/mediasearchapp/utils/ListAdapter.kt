package com.app.mediasearchapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.mediasearchapp.R
import com.app.mediasearchapp.model.Result

class ListAdapter(
    private val context: Context,
    private val items: MutableList<Result>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        val txtItem = holder.itemView.findViewById<TextView>(R.id.txtItem)
        txtItem.text = item.trackName
    }

    override fun getItemCount() = items.size
}