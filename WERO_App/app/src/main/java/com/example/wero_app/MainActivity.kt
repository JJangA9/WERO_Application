package com.example.wero_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewpager2 : ViewPager2
    private val tabTextList = arrayListOf("나의 이야기", "우편함", "모두의 위로")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, WriteDiary::class.java)
            startActivity(intent)
        }
    }
    private fun init() {
        tabLayout = findViewById(R.id.tab_layout)
        viewpager2 = findViewById(R.id.pager)
        viewpager2.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewpager2) {tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
}
