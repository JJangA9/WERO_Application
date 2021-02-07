package com.example.wero_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        diaryList = arrayListOf<MyDiaryRecyclerViewItem>(
                MyDiaryRecyclerViewItem("21.02.07", "막 엣날에 막 뭐 보며는 다 빅뱅 덕질하고 그랬을거야\n 나 아는 꼬마애가 막 빅뱅 얘기만 해면 죽을라 했거든"),
                MyDiaryRecyclerViewItem("21.02.06", "나나양님 뭐 여기 한버도 안왔는데 뭘 덕질을 해\n 유튜브는 다들 많이 본다고 하더라고\n바람의~~상처~~!~~ 그분 나랑 합방하려고 아크 하신 분이라고요?"),
                MyDiaryRecyclerViewItem("21.02.05", "그래요?\n그래?나는 김돔님한테 가서 어 팬입니다~ 그ㅐㄹㅆ는대~\n김도님이 부담스러워하시긴 하더라 ㅋㅋ")

        )

        val mAdapter = MyDiaryAdapter(mcontext, diaryList)
        val mRecyclerview = view.findViewById<RecyclerView>(R.id.recycler_diary)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)


        return view
    }


}