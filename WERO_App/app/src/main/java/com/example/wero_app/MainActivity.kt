package com.example.wero_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.user.UserApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var fragType = 0;
    private lateinit var tabLayout: TabLayout
    private lateinit var viewpager2 : ViewPager2
    private val tabTextList = arrayListOf("나의 이야기", "우편함", "모두의 위로")

    //retrofit
    val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
    val service = retrofit.create(RetrofitService::class.java)
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        replyListToDiary()

        userId = intent.getStringExtra("userId")
        if (userId != null) {
            Log.d("kakao", userId!!)
        }
        else {
            Log.d("kakao", "userId is null")
            getUserId()  // 콜백이라 의미가 있는지 모르겠음
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayShowCustomEnabled(true)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, WriteDiary::class.java)
            intent.putExtra("kakaoId", userId)
            startActivity(intent)
        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab==null)
                    fab.hide()
                else
                    if(tab.position==0)
                        fab.show()
                    else
                        fab.hide()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> {
                //settings button
                val intent : Intent = Intent(this, Setting::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.postbox -> {
                //postbox button
                val intent : Intent = Intent(this, ReplyList::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
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

    private fun getUserId() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                userId = user.id.toString()
            }
        }
    }

    fun changeFragmentHasBackStack(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(id, frag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit()
    }

    fun changeFragmentNoBackStack(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(id, frag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    private fun replyListToDiary() {
        fragType = intent.getIntExtra("fragType", 0)
        val diaryId = intent.getIntExtra("diaryId", 0)
        if(fragType == 1) {
            val fab: FloatingActionButton = findViewById(R.id.fab)
            fab.hide()
            listToDiary(R.id.fragment, DiaryPage(), diaryId)
        }
    }

    fun listToDiary(id: Int, frag: Fragment, diaryId: Int) {
        supportFragmentManager.beginTransaction()
                .replace(id, frag.apply{
                    arguments = Bundle().apply {
                        putInt("diaryId", diaryId)
                    }
                })
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit()
    }

}
