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
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EveryWero : Fragment() {

    lateinit var mcontext: Context
    private var weroList = arrayListOf<WeroItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.every_wero,container,false)

        getWeroList()

        return view
    }

    private fun getWeroList() {
        val service = (activity as MainActivity).service
        service.getWeroList().enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("everywero", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (arr != null) {
                    Log.d("everywero", arr.toString())

                    for(i in 0 until arr.size()) {
                        val obj: JsonObject = arr.get(i) as JsonObject
                        val replyId = obj.get("reply_id").asInt
                        val userId = obj.get("user_from_id").asString
                        val userName = obj.get("user_name").asString
                        val content = obj.get("content").asString
                        val heart = obj.get("heart").asInt
                        weroList.add(WeroItem(replyId, userId, userName, content, heart))
                    }
                    setRecyclerView()
                } else {
                    Log.d("everywero", "null")
                }
            }
        })
    }

    private fun setRecyclerView() {
        val mAdapter = EveryWeroAdapter(mcontext, weroList)
        val mRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler_every)
        mRecyclerview?.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview?.layoutManager = lm
        mRecyclerview?.setHasFixedSize(true)
    }
}