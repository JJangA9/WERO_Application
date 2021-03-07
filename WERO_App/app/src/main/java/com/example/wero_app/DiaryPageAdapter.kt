package com.example.wero_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiaryPageAdapter(val context: Context, private val replyRecycler : ArrayList<ReplyItem>) : RecyclerView.Adapter<DiaryPageAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.diary_page_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return replyRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            showPopup(replyRecycler[position].replyId)
        }
        holder.bind(listener, replyRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val post = itemView.findViewById<TextView>(R.id.txt_content)

        fun bind(listener: View.OnClickListener, replyItem : ReplyItem, context: Context) {
            post.text = replyItem.reply
            itemView.setOnClickListener(listener)
        }
    }

    private fun showPopup(replyId: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.diary_page_alert, null)
        val btnShare = view.findViewById<Button>(R.id.btn_share)
        val btnReport = view.findViewById<Button>(R.id.btn_report)

        val alertDialog = AlertDialog.Builder(context)
                .create()
        alertDialog.setView(view)
        alertDialog.show()

        btnShare.setOnClickListener {
            Toast.makeText(context, "gg", Toast.LENGTH_SHORT).show()
            shareReply(ReplyIdData(replyId))
        }
    }

    private fun shareReply(data: ReplyIdData) {
        //retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)
        service.shareReply(data).enqueue(object : Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("diarypage", "get failure")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Toast.makeText(context, "공유 완료", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
