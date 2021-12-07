package com.example.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_holder.view.*

class historyAdapter(val context: Context , val items: ArrayList<historyModelClass> ):RecyclerView.Adapter<historyAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val llDateFormat = view.time_history_tv
        val llmenu = view.menu
        val lldelete = view.id_delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_holder,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.llDateFormat.text = item.date

        if(position % 2 == 0){
            holder.llmenu.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llmenu.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.lldelete.setOnClickListener{
            if(context is history){
                context.deleteRecord(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}