package com.example.wero_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Postbox : Fragment() {

    lateinit var mcontext: Context
    var postList = arrayListOf<PostItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.postbox,container,false)

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        var userId: String? = null
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("postbox", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                userId = user.id.toString()
                Log.d("postbox", userId!!)
                userId?.let { getPostList(it) }
            }
        }

        // swipe to refresh
        swipe.setOnRefreshListener {
            postList.clear()
            userId?.let { getPostList(it) }
            swipe.isRefreshing = false
        }

        return view
    }

    private fun getPostList(data: String) {
        val service = (activity as MainActivity).service
        service.getPostList(data).enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("postbox", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("postbox", arr.toString())

                    postList.clear()
                    for(i in 0 until arr!!.size()){
                        val obj: JsonObject = arr.get(i) as JsonObject
                        val diaryId = obj.get("diary_id").asInt
                        val userFromId = obj.get("user_from_id").asString
                        val userToId = obj.get("user_to_id").asString
                        val diaryDate = obj.get("diary_date").asString.substring(0, 10)
                        val content = obj.get("content").asString
                        val isShared = obj.get("is_shared").asInt
                        postList.add(PostItem(diaryId, userFromId, userToId, diaryDate, content, isShared))
                    }
                    setRecyclerView()
                }
                else {
                    Log.d("mydiary", "null")
                }
            }
        })
    }

    private fun setRecyclerView() {
        val mAdapter = PostboxAdapter(mcontext, postList)
        val mRecyclerview = view?.findViewById<RecyclerView>(R.id.postbox_recyclerView)
        mRecyclerview?.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview?.layoutManager = lm
        mRecyclerview?.setHasFixedSize(true)
    }


}