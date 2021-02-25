package com.example.wero_app

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MyDiary : Fragment() {

    lateinit var mcontext: Context
    var diaryList = arrayListOf<MyDiaryRecyclerViewItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.my_diary,container,false)

        val txtDate: TextView = view.findViewById(R.id.txt_yymm)
        txtDate.text = SimpleDateFormat("yyyy년 MM월").format(Date())

        val date: String = SimpleDateFormat("yyyy-MM").format(Date())
        Log.d("mydiary", date)

        getDiaryList(date)

        val imageButton = view.findViewById<ImageButton>(R.id.imgbtn_calender)

        imageButton.setOnClickListener {
            (getActivity() as MainActivity).changeFragmentNoBackStack(R.id.my_diary, MyDiary_Calendar())
        }

        return view
    }

    private fun getDiaryList(data: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-3-140-134-198.us-east-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RetrofitService::class.java)
        service.getDiaryList(data).enqueue(object : Callback<DiaryListResponse> {
            override fun onFailure(call: Call<DiaryListResponse>, t: Throwable) {
                Log.d("mydiary", "get failure")
            }

            override fun onResponse(call: Call<DiaryListResponse>, response: Response<DiaryListResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("mydiary", arr.toString())

                    for(i in 0 until arr!!.size()){
                        val obj: JsonObject = arr.get(i) as JsonObject
                        diaryList.add(MyDiaryRecyclerViewItem(obj.get("diary_date").asString.substring(0, 10), obj.get("content").asString))
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