package com.example.wero_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils

class PostboxAdapter(val context: Context, private val postRecycler : ArrayList<PostboxRecyclerViewItem>) : RecyclerView.Adapter<PostboxAdapter.Holder>()  {

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
            context.startActivity(intent)
        }
        holder.bind(listener, postRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nickName = itemView.findViewById<TextView>(R.id.nickName)
        private val letter = itemView.findViewById<TextView>(R.id.postboxTxt)

        fun bind(listener: View.OnClickListener, postBoxItem : PostboxRecyclerViewItem, context: Context) {
            nickName.text = postBoxItem.nickName
            letter.text = postBoxItem.letter
            itemView.setOnClickListener(listener)
        }
    }
}

class PostboxRecyclerViewItem(val nickName: String, val letter: String)