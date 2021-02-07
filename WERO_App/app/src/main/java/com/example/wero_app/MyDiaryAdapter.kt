package com.example.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyDiaryAdapter(val context: Context, val diaryRecycler : ArrayList<MyDiaryRecyclerViewItem>) : RecyclerView.Adapter<MyDiaryAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_diary_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(diaryRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView?.findViewById<TextView>(R.id.txt_date)
        val diary = itemView?.findViewById<TextView>(R.id.txt_content)

        fun bind(diaryItem : MyDiaryRecyclerViewItem, context: Context) {
            date.text = diaryItem.date
            diary.text = diaryItem.letter
        }

    }
}

class MyDiaryRecyclerViewItem(val date: String, val letter: String) {
}