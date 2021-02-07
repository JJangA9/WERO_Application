package com.example.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyDiary_CalendarAdapter (val context: Context, val letterRecycler : ArrayList<MyDiary_Calendar_RecyclerViewItem>) : RecyclerView.Adapter<MyDiary_CalendarAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return letterRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(letterRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letter = itemView?.findViewById<TextView>(R.id.bottomSheetTxt)

        fun bind(postBoxItem : MyDiary_Calendar_RecyclerViewItem, context: Context) {
            letter.text = postBoxItem.letter
        }
    }
}