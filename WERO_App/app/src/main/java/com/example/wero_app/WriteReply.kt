package com.example.wero_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WriteReply : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_reply)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val userId = intent.getStringExtra("userId")
        val diaryId = intent.getIntExtra("diaryId", 0)
        val content = intent.getStringExtra("content")
        val date: String = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date())

        val txtContent = findViewById<TextView>(R.id.txt_content)
        val editReply = findViewById<EditText>(R.id.edit_reply)
        val btnReply = findViewById<Button>(R.id.btn_send)

        Log.d("writereply", "diaryId: $diaryId")

        txtContent.text = content
        btnReply.setOnClickListener {
            putData(ReplyData(userId, diaryId, date, editReply.text.toString()))
        }
    }

    private fun putData(data: ReplyData) {
        val service = retrofit.create(RetrofitService::class.java)
        service.sendReply(data).enqueue(object: Callback<ReplyResponse> {
            override fun onFailure(call: Call<ReplyResponse>, t: Throwable) {
                Toast.makeText(this@WriteReply, "전송 실패", Toast.LENGTH_SHORT).show()
                Log.d("writereply", t.message.toString())
            }

            override fun onResponse(call: Call<ReplyResponse>, response: Response<ReplyResponse>) {
                val msg = response.body()
                Toast.makeText(this@WriteReply, msg?.message, Toast.LENGTH_SHORT).show()
                data.userId?.let { deleteData(data.diaryId, it) }
                finish()
            }
        })
    }

    private fun deleteData(diaryId: Int, userId: String) {
        val service = retrofit.create(RetrofitService::class.java)
        service.deletePost(diaryId, userId).enqueue(object: Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(this@WriteReply, "삭제 실패", Toast.LENGTH_SHORT).show()
                Log.d("writereply", t.message.toString())
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val msg = response.body()
                Toast.makeText(this@WriteReply, msg?.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }


}