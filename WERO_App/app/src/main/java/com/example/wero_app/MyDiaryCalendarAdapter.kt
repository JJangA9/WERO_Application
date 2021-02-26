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

class MyDiaryCalendarAdapter (val context: Context, val letterRecycler : ArrayList<MyDiaryCalendarRecyclerViewItem>) : RecyclerView.Adapter<MyDiaryCalendarAdapter.Holder>() {
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
            (ContextUtils.getActivity(context) as MainActivity).changeFragmentHasBackStack(R.id.my_diary_layout, DiaryPage())
        }
        holder?.bind(listener, letterRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letter = itemView?.findViewById<TextView>(R.id.txt_bottom_sheet)

        fun bind(listener: View.OnClickListener, postBoxItem : MyDiaryCalendarRecyclerViewItem, context: Context) {
            letter.text = postBoxItem.letter
            itemView.setOnClickListener(listener)
        }
    }
}

class MyDiaryCalendarRecyclerViewItem (val letter: String)