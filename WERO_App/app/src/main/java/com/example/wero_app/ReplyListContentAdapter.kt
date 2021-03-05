package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils

class ReplyListContentAdapter(val context: Context, val contentRecycler : ArrayList<ReplyListRecyclerViewContentItem>, val replyItemList: ArrayList<ReplyItem>) : RecyclerView.Adapter<ReplyListContentAdapter.Holder>()  {

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
            //(context as ReplyList).finish()
            val intent = Intent(context, MainActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("fragType", 1)
            intent.putExtra("diaryId", contentRecycler[position].diaryId)
            context.startActivity(intent)

        }
        holder.bind(listener, contentRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content: TextView = itemView.findViewById<TextView>(R.id.txt_content)
        private val replyList = arrayListOf<ReplyListRecyclerViewReplyItem>()
        private val mRecyclerview = itemView.findViewById<RecyclerView>(R.id.recycler_reply)

        fun bind(listener: View.OnClickListener, contentItem : ReplyListRecyclerViewContentItem, context: Context) {
            content.text = contentItem.content

            for(i in 0 until replyItemList.size) {
                if(contentItem.diaryId == replyItemList[i].diaryId) replyList.add(
                    ReplyListRecyclerViewReplyItem(replyItemList[i].reply)
                )
            }
            val replyAdapter = ReplyListReplyAdapter(context, replyList)
            mRecyclerview.adapter = replyAdapter
            val lm = LinearLayoutManager(context)
            mRecyclerview.layoutManager = lm
            mRecyclerview.setHasFixedSize(true)
            mRecyclerview.height
            itemView.setOnClickListener(listener)
        }
    }
}