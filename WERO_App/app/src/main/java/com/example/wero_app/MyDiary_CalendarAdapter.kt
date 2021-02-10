package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils

class MyDiary_CalendarAdapter (val context: Context, val letterRecycler : ArrayList<MyDiary_Calendar_RecyclerViewItem>) : RecyclerView.Adapter<MyDiary_CalendarAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return letterRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            (ContextUtils.getActivity(context) as MainActivity).changeFragment3()
        }
        holder?.bind(listener, letterRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letter = itemView?.findViewById<TextView>(R.id.bottomSheetTxt)

        fun bind(listener: View.OnClickListener, postBoxItem : MyDiary_Calendar_RecyclerViewItem, context: Context) {
            letter.text = postBoxItem.letter
            itemView.setOnClickListener(listener)
        }
    }
}