package com.wero.wero_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditDiary : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
        .addConverterFactory(GsonConverterFactory.create()).build()

    private lateinit var btnDate: Button
    private lateinit var btnSave: Button
    private lateinit var editContent: EditText
    private var diaryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_diary)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        btnDate = findViewById<Button>(R.id.btn_date)
        editContent = findViewById<EditText>(R.id.edit_content)
        btnSave = findViewById<Button>(R.id.btn_save)

        diaryId = intent.getIntExtra("diaryId", 0)
        btnDate.text = intent.getStringExtra("txtDate")
        editContent.setText(intent.getStringExtra("txtContent"))

        btnSave.setOnClickListener {
            val content = editContent.text.toString()
            updateDiary(DiaryItem(diaryId, null, btnDate.text.toString(), content, null))
        }
    }

    private fun updateDiary(data: DiaryItem) {
        val service = retrofit.create(RetrofitService::class.java)
        service.updateDiary(data).enqueue(object: Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(this@EditDiary, "저장 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val msg = response.body()
                Toast.makeText(this@EditDiary, msg?.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}