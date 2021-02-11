package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReplyListReplyAdapter(val context: Context, val replyRecycler : ArrayList<ReplyListRecyclerViewReplyItem>) : RecyclerView.Adapter<ReplyListReplyAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.reply_list_recyclerview_item2, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return replyRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
        }
        holder?.bind(listener, replyRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reply = itemView?.findViewById<TextView>(R.id.txt_reply)

        fun bind(listener: View.OnClickListener, replyItem : ReplyListRecyclerViewReplyItem, context: Context) {
            reply.text = replyItem.reply
            itemView.setOnClickListener(listener)
        }
    }
}