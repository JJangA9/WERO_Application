package com.wero.wero_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EveryWeroAdapter(val context: Context, private val weroRecycler : ArrayList<WeroItem>) : RecyclerView.Adapter<EveryWeroAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.everywero_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return weroRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(weroRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName = itemView.findViewById<TextView>(R.id.txt_nickname)
        private val content = itemView.findViewById<TextView>(R.id.txt_wero)
        //private val heartNum = itemView.findViewById<TextView>(R.id.txt_heart_number)

        fun bind(postBoxItem : WeroItem, context: Context) {
            if(postBoxItem.userName == "null") userName.text = "익명"
            else userName.text = "익명"
            //else userName.text = postBoxItem.userName
            content.text = postBoxItem.content
            //heartNum.text = postBoxItem.heart.toString()
        }
    }
}
