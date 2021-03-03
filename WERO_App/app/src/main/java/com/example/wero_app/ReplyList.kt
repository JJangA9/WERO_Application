package com.example.wero_app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReplyList : AppCompatActivity() {

    lateinit var mcontext: Context
    var contentList = arrayListOf<ReplyListRecyclerViewContentItem>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reply_list)

        contentList = arrayListOf<ReplyListRecyclerViewContentItem>(
                ReplyListRecyclerViewContentItem("고민들어줘"),
                ReplyListRecyclerViewContentItem("취뽀"),
                ReplyListRecyclerViewContentItem("슬퍼")
        )

        val mAdapter = ReplyListContentAdapter(this, contentList)
        val mRecyclerview = findViewById<RecyclerView>(R.id.recycler_diary)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)

    }

    fun getReplyList() {

    }
}

class ReplyListRecyclerViewContentItem (val content: String)
class ReplyListRecyclerViewReplyItem (val reply: String)
class ReplyItem(val replyId: Int, val diaryId: Int, val userId: String, val replyDate: String, val content: String, val isShared: Int, val isChecked: Int)