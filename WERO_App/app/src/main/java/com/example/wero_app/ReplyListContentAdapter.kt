package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReplyListContentAdapter(val context: Context, val contentRecycler : ArrayList<ReplyListRecyclerViewContentItem>) : RecyclerView.Adapter<ReplyListContentAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.reply_list_recyclerview_content_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return contentRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
        }
        holder?.bind(listener, contentRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content: TextView = itemView?.findViewById<TextView>(R.id.txt_content)
        private var replyList = arrayListOf<ReplyListRecyclerViewReplyItem>()
        private val mRecyclerview = itemView.findViewById<RecyclerView>(R.id.recycler_reply)

        fun bind(listener: View.OnClickListener, contentItem : ReplyListRecyclerViewContentItem, context: Context) {
            content.text = contentItem.content

            replyList = arrayListOf<ReplyListRecyclerViewReplyItem>(
                    ReplyListRecyclerViewReplyItem("a"),
                    ReplyListRecyclerViewReplyItem("b"),
                    ReplyListRecyclerViewReplyItem("c")
            )
            val replyAdapter = ReplyListReplyAdapter(context, replyList)
            mRecyclerview.adapter = replyAdapter
            val lm = LinearLayoutManager(context)
            mRecyclerview.layoutManager = lm
            mRecyclerview.setHasFixedSize(true)
            mRecyclerview.height

        }
    }
}