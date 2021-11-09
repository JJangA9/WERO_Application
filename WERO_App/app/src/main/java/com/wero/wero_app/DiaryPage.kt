package com.wero.wero_app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiaryPage : Fragment() {

    lateinit var mcontext: Context
    lateinit var userId: String
    var replyList = arrayListOf<ReplyItem>()
    var diaryId: Int = 0

    lateinit var txtDate: TextView
    lateinit var txtContent: TextView
    lateinit var btnEdit: Button
    lateinit var btnDelete: Button
    lateinit var swipe: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.diary_page,container,false)

        swipe = view.findViewById(R.id.swipe)
        txtDate = view.findViewById(R.id.txt_date)
        txtContent = view.findViewById(R.id.txt_content)
        btnEdit = view.findViewById(R.id.btn_edit)
        btnDelete = view.findViewById(R.id.btn_delete)

        // get data from server and set view items
        diaryId = arguments?.getInt("diaryId")!!
        getDiaryData()
        getReplyData()

        // button
        btnEdit.setOnClickListener {
            val intent = Intent(activity, EditDiary::class.java).apply{
                putExtra("txtDate", txtDate.text)
                putExtra("txtContent", txtContent.text)
                putExtra("diaryId", diaryId)
            }
            startActivity(intent)
        }
        btnDelete.setOnClickListener { deleteAlert() }

        // swipe to refresh
        swipe.setOnRefreshListener {
            getReplyData()
            swipe.isRefreshing = false
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        getDiaryData()
        getReplyData()
    }

    private fun getUserId() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("diarypage", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                userId = user.id.toString()
                Log.d("diarypage", userId)
            }
        }
    }

    private fun deleteAlert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("일기를 삭제하실 건가요?")

        builder.setPositiveButton("네") { dialog, id ->
            deleteDiary()
        }
        builder.setNegativeButton("아니요") { dialog, id ->

        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteDiary() {
        val service = (activity as MainActivity).service
        service.deleteDiary(diaryId).enqueue(object : Callback<ServerResponse> {
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("diarypage", "delete failure")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val msg = response.body()
                Log.d("diarypage", msg?.message.toString())
                Toast.makeText(activity, msg?.message, Toast.LENGTH_SHORT).show()
                val fragmentManager = (activity as MainActivity).supportFragmentManager
                fragmentManager.beginTransaction().remove(this@DiaryPage).commit()
                fragmentManager.popBackStack()
            }
        })
    }

    private fun getReplyData() {
        val service = (activity as MainActivity).service
        service.getReply(diaryId).enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("diarypage", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("diarypage", arr.toString())

                    replyList.clear()
                    for(i in 0 until arr!!.size()){
                        val obj: JsonObject = arr.get(i) as JsonObject
                        val replyId = obj.get("reply_id").asInt
                        val userFromId = obj.get("user_from_id").asString
                        val replyDate = obj.get("reply_date").asString.substring(0, 10)
                        val reply = obj.get("content").asString
                        replyList.add(ReplyItem(replyId, diaryId, userFromId, null, replyDate, null, reply))
                    }
                    setRecyclerView()
                }
                else {
                    Log.d("mydiary", "null")
                }
            }
        })
    }

    private fun getDiaryData() {
        val service = (activity as MainActivity).service
        service.getDiary(diaryId).enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("diarypage", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (arr != null) {
                    Log.d("diarypage", arr.toString())

                    val obj: JsonObject = arr.get(0) as JsonObject
                    val diaryDate = obj.get("diary_date").asString
                    val content = obj.get("content").asString
                    val isShared = obj.get("is_shared").asString

                    txtContent.text = content
                    txtDate.text = diaryDate.substring(0, 10)

                    setRecyclerView()
                } else {
                    txtContent.text = "일기 불러오기 실패"
                    Log.d("mydiary", "null")
                }
            }
        })
    }

    private fun setRecyclerView() {
        val mAdapter = DiaryPageAdapter(mcontext, replyList)
        val mRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler_reply)
        mRecyclerview?.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview?.layoutManager = lm
        mRecyclerview?.setHasFixedSize(true)
    }

}

