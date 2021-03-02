package com.example.wero_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WriteReply : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_reply)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val txtContent = findViewById<TextView>(R.id.txt_content)
        val editReply = findViewById<EditText>(R.id.edit_reply)
        val btnReply = findViewById<Button>(R.id.btn_send)

        txtContent.text = intent.getStringExtra("content")
        btnReply.setOnClickListener {

        }
    }


}