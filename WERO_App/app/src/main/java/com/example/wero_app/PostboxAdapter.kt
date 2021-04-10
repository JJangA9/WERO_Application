package com.example.wero_app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val longListener = View.OnLongClickListener {
            showPopup(postRecycler[position].diaryId, postRecycler[position].userToId)
            return@OnLongClickListener false
        }
        holder.bind(listener, longListener, postRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName = itemView.findViewById<TextView>(R.id.txt_name)
        private val content = itemView.findViewById<TextView>(R.id.txt_content)

        fun bind(listener: View.OnClickListener, longListener: View.OnLongClickListener, postBoxItem : PostItem, context: Context) {
            if(postBoxItem.userName == "null") txtName.text = "익명"
            else txtName.text = postBoxItem.userName
            content.text = postBoxItem.content
            itemView.setOnClickListener(listener)
            itemView.setOnLongClickListener(longListener)
        }
    }

    private fun showPopup(diaryId: Int, userToId: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.postbox_alert, null)
        val btnReport = view.findViewById<Button>(R.id.btn_report)
        val btnDelete = view.findViewById<Button>(R.id.btn_delete)

        val alertDialog = AlertDialog.Builder(context)
                .create()
        alertDialog.setView(view)
        alertDialog.show()

        btnDelete.setOnClickListener {
            deleteAlert(diaryId, userToId)
            alertDialog.dismiss()
        }
    }

    private fun deleteAlert(diaryId: Int, userToId: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("편지를 삭제하실 건가요?")

        builder.setPositiveButton("네") { dialog, id ->
            deleteReply(diaryId, userToId)
        }
        builder.setNegativeButton("아니요") { dialog, id ->

        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteReply(diaryId: Int, userToId: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)
        service.deletePost(diaryId, userToId).enqueue(object : Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("postbox", "delete failure")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val msg = response.body()
                Log.d("postbox", msg?.message.toString())
            }
        })
    }
}

