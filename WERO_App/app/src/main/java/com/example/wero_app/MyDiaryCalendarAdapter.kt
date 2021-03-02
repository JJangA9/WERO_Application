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

class MyDiaryCalendarAdapter (val context: Context, private val diaryRecycler : ArrayList<DiaryItem>) : RecyclerView.Adapter<MyDiaryCalendarAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            (ContextUtils.getActivity(context) as MainActivity).listToDiary(R.id.my_diary_layout, DiaryPage(), diaryRecycler[position])
        }
        holder.bind(listener, diaryRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diary = itemView?.findViewById<TextView>(R.id.txt_bottom_sheet)

        fun bind(listener: View.OnClickListener, postBoxItem: DiaryItem, context: Context) {
            diary.text = postBoxItem.content
            itemView.setOnClickListener(listener)
        }
    }
}


