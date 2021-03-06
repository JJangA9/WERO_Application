package com.example.wero_app

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils

class PostboxAdapter(val context: Context, private val postRecycler : ArrayList<PostItem>) : RecyclerView.Adapter<PostboxAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.postbox_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return postRecycler.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            val intent = Intent(context, WriteReply::class.java)
            intent.putExtra("diaryId", postRecycler[position].diaryId)
            intent.putExtra("userFromId", postRecycler[position].userFromId)
            intent.putExtra("userToId", postRecycler[position].userToId) // 편지 받는 사람 id
            intent.putExtra("content", postRecycler[position].content)
            context.startActivity(intent)
        }
        holder.bind(listener, postRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName = itemView.findViewById<TextView>(R.id.txt_name)
        private val content = itemView.findViewById<TextView>(R.id.txt_content)

        fun bind(listener: View.OnClickListener, postBoxItem : PostItem, context: Context) {
            txtName.text = postBoxItem.userFromId
            content.text = postBoxItem.content
            itemView.setOnClickListener(listener)
        }
    }
}

