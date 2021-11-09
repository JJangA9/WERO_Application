package com.wero.wero_app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Setting : AppCompatActivity() {

    //retrofit
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
    private val service = retrofit.create(RetrofitService::class.java)

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    lateinit var txtName: TextView
    lateinit var btnEdit: Button
    lateinit var btnLogout: ImageView //변수명 수정
    lateinit var switchPush: Switch

    lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayShowCustomEnabled(true)

        txtName = findViewById(R.id.txt_name)
        btnEdit = findViewById(R.id.btn_edit)
        btnLogout = findViewById(R.id.img_logout)
        switchPush = findViewById(R.id.switch_alarm)
        btnEdit.setOnClickListener {
            editNickname()
        }
        btnLogout.setOnClickListener {
            logout()
        }

        getUserId()
    }

    private fun logout() {
        // go initial activity (login activity)
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        ActivityCompat.finishAffinity(this)

        // remove all data
        sharedPreferences = this.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()


    }
    private fun getUserId() {
        sharedPreferences = this.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        if(!sharedPreferences.getString("userId", "").isNullOrBlank()) {
            userId = sharedPreferences.getString("userId", "")!!
            getName(userId)
        }
        else {
            txtName.text = "닉네임 불러오기 실패"
        }
    }
    private fun getName(userId: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        service.getName(userId).enqueue(object: Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                val obj: JsonObject = arr?.get(0) as JsonObject
                val userName = obj.get("user_name").asString
                if(userName.equals("null")) txtName.text = "익명"
                else txtName.text = userName
            }
        })
    }
    private fun editNickname() {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.setting_alert, null)
        val btnEdit = view.findViewById<Button>(R.id.btn_edit)

        val alertDialog = AlertDialog.Builder(this)
                .create()
        alertDialog.setView(view)
        alertDialog.show()

        btnEdit.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}