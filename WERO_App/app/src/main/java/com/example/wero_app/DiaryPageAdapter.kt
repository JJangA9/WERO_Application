package com.example.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryPageAdapter(val context: Context, private val replyRecycler : ArrayList<DiaryPageRecyclerViewItem>) : RecyclerView.Adapter<DiaryPageAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.diary_page_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return replyRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(replyRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val letter = itemView.findViewById<TextView>(R.id.txt_content)

        fun bind(replyItem : DiaryPageRecyclerViewItem, context: Context) {
            letter.text = replyItem.letter
        }
    }
}

class DiaryPageRecyclerViewItem(val letter: String) {
}