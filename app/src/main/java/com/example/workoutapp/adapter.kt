package com.example.workoutapp

import android.content.Context
import android.graphics.Color
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_123.view.*

class Exerciseadapter(val items: ArrayList<excercise>,val context: Context): RecyclerView.Adapter<Exerciseadapter.Viewholder>() {
    class Viewholder(view: View): RecyclerView.ViewHolder(view){
        val tvtext = view.recyler_text_number
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val layout = LayoutInflater.from(context).inflate(R.layout.view_123,parent,false)
        return Viewholder(layout)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val model: excercise = items[position]
        holder.tvtext.text = model.getId().toString()

        if(model.getisSelected()){
         holder.tvtext.background = ContextCompat.getDrawable(context,R.drawable.active_bg)
        }else if(model.getisCompleted()){
            holder.tvtext.background = ContextCompat.getDrawable(context,R.drawable.finish_)
            holder.tvtext.setTextColor(Color.parseColor("#FFFFFFFF"))
        }else{
            holder.tvtext.background = ContextCompat.getDrawable(context,R.drawable.bg_n0_123)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}