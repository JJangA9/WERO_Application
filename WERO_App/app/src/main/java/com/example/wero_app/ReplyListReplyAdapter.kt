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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReplyListReplyAdapter(val context: Context, private val replyRecycler : ArrayList<ReplyListRecyclerViewReplyItem>) : RecyclerView.Adapter<ReplyListReplyAdapter.Holder>()  {

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
            intent.putExtra("diaryId", replyRecycler[position].diaryId)
            context.startActivity(intent)
            checkReply(DiaryIdData(replyRecycler[position].diaryId))
        }
        holder.bind(listener, replyRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reply = itemView.findViewById<TextView>(R.id.txt_reply)

        fun bind(listener: View.OnClickListener, replyItem : ReplyListRecyclerViewReplyItem, context: Context) {
            reply.text = replyItem.reply
            itemView.setOnClickListener(listener)
        }
    }

    private fun checkReply(data: DiaryIdData) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)
        service.checkReply(data).enqueue(object : Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("replylist", "get failure")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val msg = response.body()
                Log.d("replylist", msg?.message.toString())
            }
        })
    }
}