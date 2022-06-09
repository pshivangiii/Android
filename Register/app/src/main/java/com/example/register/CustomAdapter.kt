package com.example.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context)
                  .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val ItemsViewModel = dataList[position]
        holder.textView.text = ItemsViewModel.name
        holder.textView2.text = ItemsViewModel.number
    }

    override fun getItemCount(): Int
    {
        return dataList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
    }
}
