package com.example.wero_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url

class Register : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var userPwd: EditText
    lateinit var userEmail: EditText
    lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userName = findViewById(R.id.userName)
        userPwd = findViewById(R.id.pwd)
        userEmail = findViewById(R.id.email)
        registerBtn = findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener { attemptJoin() }

    }

    private fun attemptJoin() {

        val name = userName.text.toString()
        val pwd = userPwd.text.toString()
        val email = userEmail.text.toString()

        val data = JoinData(email, pwd, name)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-18-191-207-96.us-east-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        service.userJoin(data).enqueue(object: Callback<JoinResponse> {
            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Toast.makeText(this@Register, "가입에 실패했습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                val login = response.body()
                Toast.makeText(this@Register, login?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
