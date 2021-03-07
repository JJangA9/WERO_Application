package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MyDiary : Fragment() {

    lateinit var mcontext: Context
    var diaryList = arrayListOf<DiaryItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.my_diary,container,false)

        val txtDate: TextView = view.findViewById(R.id.txt_yymm)
        val imageButton = view.findViewById<ImageButton>(R.id.imgbtn_calender)

        txtDate.text = SimpleDateFormat("yyyy년 MM월").format(Date())
        imageButton.setOnClickListener {
            (activity as MainActivity).changeFragmentNoBackStack(R.id.my_diary, MyDiaryCalendar())
        }

        val date: String = SimpleDateFormat("yyyy-MM").format(Date())
        var userId: String?
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.d("mydiary", "사용자 정보 요청 실패", error)
            }
            else {
                userId = user?.id?.toString()
                userId?.let { getDiaryList(it, date) }
            }
        }

        return view
    }

    private fun getDiaryList(userId: String, date: String) {
        val service = (activity as MainActivity).service
        service.getDiaryList(userId, date).enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("mydiary", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("mydiary", arr.toString())

                    diaryList.clear()
                    for(i in 0 until arr!!.size()){
                        val obj: JsonObject = arr.get(i) as JsonObject
                        val diaryId = obj.get("diary_id").asInt
                        val userId = obj.get("user_id").asString
                        val diaryDate = obj.get("diary_date").asString.substring(0, 10)
                        val content = obj.get("content").asString
                        val isShared = obj.get("is_shared").asInt
                        diaryList.add(DiaryItem(diaryId, userId, diaryDate, content, isShared))
                        Log.d("mydiary", diaryList.toString())
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
        val mAdapter = MyDiaryAdapter(mcontext, diaryList)
        val mRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler_diary)
        mRecyclerview?.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview?.layoutManager = lm
        mRecyclerview?.setHasFixedSize(true)
    }

}