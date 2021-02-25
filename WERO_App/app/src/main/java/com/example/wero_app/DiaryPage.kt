package com.example.wero_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiaryPage : Fragment() {
    lateinit var mcontext: Context
    var replyList = arrayListOf<DiaryPageRecyclerViewItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.diary_page,container,false)

        val txtDate = view.findViewById<TextView>(R.id.txt_date)
        val txtContent = view.findViewById<TextView>(R.id.txt_content)

        txtDate.text = arguments?.getString("diaryDate")
        txtContent.text = arguments?.getString("content")

        replyList = arrayListOf<DiaryPageRecyclerViewItem>(
                DiaryPageRecyclerViewItem("곱창 ! 껍데기 ! 닭발 ! 소주 ! \n 배고팡"),
                DiaryPageRecyclerViewItem("취업시켜주세요 ~ ~ ~ ~ "),
                DiaryPageRecyclerViewItem("Congratulation 넌 참 대단해 \n"
                        + "Congratulation 어쩜 그렇게 \n"
                        + "아무렇지 않아 하며 날 짓밟아 \n웃는 얼굴을 보니 다 잊었나봐")

        )

        val mAdapter = DiaryPageAdapter(mcontext, replyList)
        val mRecyclerview = view.findViewById<RecyclerView>(R.id.recycler_reply)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)


        return view
    }
}