package com.example.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EveryWeroAdapter(val context: Context, private val letterRecycler : ArrayList<EveryWeroRecyclerViewItem>) : RecyclerView.Adapter<EveryWeroAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.everywero_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return letterRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(letterRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nickName = itemView.findViewById<TextView>(R.id.txt_nickname)
        private val wero = itemView.findViewById<TextView>(R.id.txt_wero)
        private val heartNum = itemView.findViewById<TextView>(R.id.txt_heart_number)

        fun bind(postBoxItem : EveryWeroRecyclerViewItem, context: Context) {
            nickName.text = postBoxItem.nickName
            wero.text = postBoxItem.wero
            heartNum.text = postBoxItem.heartNum
        }
    }
}

class EveryWeroRecyclerViewItem(val nickName: String, val wero: String, val heartNum: String)