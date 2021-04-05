package com.example.wero_app

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Setting : AppCompatActivity() {

    lateinit var btnEdit: Button
    lateinit var btnLogout: ImageView //변수명 수정
    lateinit var switchPush: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayShowCustomEnabled(true);

        btnEdit = findViewById(R.id.btn_edit)
        btnEdit.setOnClickListener {
            editNickname()
        }
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