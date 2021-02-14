package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReplyListReplyAdapter(val context: Context, val replyRecycler : ArrayList<ReplyListRecyclerViewReplyItem>) : RecyclerView.Adapter<ReplyListReplyAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.reply_list_recyclerview_reply_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return replyRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            //(context as ReplyList).finish()
            val intent = Intent(context, MainActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("fragType", 1)
            context.startActivity(intent)
        }
        holder?.bind(listener, replyRecycler[position], context)
    }

    inner class setSize(v: View) {
        val r = v.findViewById<TextView>(R.id.txt_reply)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reply = itemView?.findViewById<TextView>(R.id.txt_reply)

        fun bind(listener: View.OnClickListener, replyItem : ReplyListRecyclerViewReplyItem, context: Context) {
            reply.text = replyItem.reply
            itemView.setOnClickListener(listener)
        }
    }
}