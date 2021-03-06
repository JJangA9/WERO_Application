package com.example.wero_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReplyList : AppCompatActivity() {

    var contentList = arrayListOf<ReplyListRecyclerViewContentItem>()
    var replyItemList = arrayListOf<ReplyItem>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reply_list)

        var userId: String? = null
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("postbox", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                userId = user.id.toString()
                Log.d("postbox", userId!!)
                userId?.let { getReplyList(it) }
            }
        }


    }

    private fun getReplyList(userId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-79-128-138.ap-northeast-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)
        service.getReplyList(userId).enqueue(object : Callback<ReplyListResponse> {
            override fun onFailure(call: Call<ReplyListResponse>, t: Throwable) {
                Log.d("replylist", "get failure")
            }

            override fun onResponse(call: Call<ReplyListResponse>, response: Response<ReplyListResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("replylist", arr.toString())

                    var prev: Int = -1;
                    replyItemList.clear()
                    for(i in 0 until arr!!.size()){
                        val obj: JsonObject = arr.get(i) as JsonObject
                        val replyId = obj.get("reply_id").asInt
                        val diaryId = obj.get("diary_id").asInt
                        val userFromId = obj.get("user_from_id").asString
                        val userToId = obj.get("user_to_id").asString
                        val replyDate = obj.get("reply_date").asString.substring(0, 10)
                        val content = obj.get("content").asString
                        val reply = obj.get("reply").asString
                        replyItemList.add(ReplyItem(replyId, diaryId, userFromId, userToId, replyDate, content, reply))

                        if(i > 0 && prev == diaryId) continue
                        contentList.add(ReplyListRecyclerViewContentItem(diaryId, content))
                        prev = diaryId
                    }
                    setRecyclerView()
                }
                else {
                    Log.d("replylist", "null")
                }
            }
        })
    }

    fun setRecyclerView() {
        val mAdapter = ReplyListContentAdapter(this, contentList, replyItemList)
        val mRecyclerview = findViewById<RecyclerView>(R.id.recycler_diary)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)
    }
}

class ReplyListRecyclerViewContentItem (val diaryId: Int, val content: String)
class ReplyListRecyclerViewReplyItem (val reply: String, val diaryId: Int)



