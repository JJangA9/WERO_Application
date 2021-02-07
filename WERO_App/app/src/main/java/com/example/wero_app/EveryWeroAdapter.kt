package com.example.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EveryWeroAdapter(val context: Context, val letterRecycler : ArrayList<EveryWeroRecyclerViewItem>) : RecyclerView.Adapter<EveryWeroAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.everywero_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return letterRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(letterRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickName = itemView?.findViewById<TextView>(R.id.nickName)
        val letter = itemView?.findViewById<TextView>(R.id.postboxTxt)
        val heartNum = itemView?.findViewById<TextView>(R.id.heartNumber)

        fun bind(postBoxItem : EveryWeroRecyclerViewItem, context: Context) {
            nickName.text = postBoxItem.nickName
            letter.text = postBoxItem.letter
            heartNum.text = postBoxItem.heartNum
        }
    }
}