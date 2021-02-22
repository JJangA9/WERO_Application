package com.example.wero_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WriteDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_diary)

        val kakaoId = intent.getStringExtra("kakaoId")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val btnDate = findViewById<Button>(R.id.btn_date)
        val editContent = findViewById<EditText>(R.id.edit_content)
        val checkBoxSend = findViewById<CheckBox>(R.id.checkbox_want_send)
        val btnSave = findViewById<Button>(R.id.btn_save)


        btnDate.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())
        btnSave.setOnClickListener {
            val content = editContent.text.toString()
            val date: String = SimpleDateFormat("yyyy-MM-dd").format(Date())
            var isShared = 0
            if(checkBoxSend.isChecked) isShared = 1
            putData(DiaryData(kakaoId, date, content, isShared))
            finish()
        }
    }

    private fun putData(data: DiaryData) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-140-134-198.us-east-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        service.saveDiary(data).enqueue(object: Callback<DiaryResponse> {
            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {
                Toast.makeText(this@WriteDiary, "저장 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {
                val login = response.body()
                Toast.makeText(this@WriteDiary, login?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}