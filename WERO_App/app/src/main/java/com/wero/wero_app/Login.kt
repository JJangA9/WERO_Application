package com.wero.wero_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Login : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var kakaoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sharedPreferences = this.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        if(!sharedPreferences.getString("userId", "").isNullOrBlank()) {
            getUserId()
        }

/*
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            val registerIntent = Intent(this, Register::class.java)
            startActivity(registerIntent)
        }
*/
        // sharedPreference 데이터가 사라졌거나, 처음 로그인하는 경우
        val btnKakao: ImageButton = findViewById(R.id.btn_kakao_login)
        btnKakao.setOnClickListener {
            // 로그인 공통 callback 구성
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("kakao", "로그인 실패", error)
                } else if (token != null) {
                    Log.i("kakao", "로그인 성공 ${token.accessToken}")

                    getUserId()

                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }

    private fun getUserId() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                kakaoId = user.id.toString()
                if(sharedPreferences.getString("userId", "").isNullOrBlank()) {
                    editor = sharedPreferences.edit()
                    editor.putString("userId", kakaoId)
                    editor.apply()

                    putData(JoinData(kakaoId))
                }
                Log.d("kakao", kakaoId)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userId", kakaoId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun putData(data: JoinData) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        service.userJoin(data).enqueue(object: Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(this@Login, "가입에 실패했습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val login = response.body()
                Toast.makeText(this@Login, login?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
