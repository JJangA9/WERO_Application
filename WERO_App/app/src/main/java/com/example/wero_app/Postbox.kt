package com.example.wero_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Postbox : Fragment() {

    lateinit var mcontext: Context
    var letterList = arrayListOf<PostboxRecyclerViewItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.postbox,container,false)

        letterList = arrayListOf<PostboxRecyclerViewItem>(
                PostboxRecyclerViewItem("고민들어줘", "곱창 ! 껍데기 ! 닭발 ! 소주 ! \n 배고팡"),
                PostboxRecyclerViewItem("취뽀", "취업시켜주세요 ~ ~ ~ ~ "),
                PostboxRecyclerViewItem("슬퍼", "Congratulation 넌 참 대단해 \n"
                        + "Congratulation 어쩜 그렇게 \n"
                        + "아무렇지 않아 하며 날 짓밟아 \n웃는 얼굴을 보니 다 잊었나봐")

        )

        val mAdapter = PostboxAdapter(mcontext, letterList)
        val mRecyclerview = view.findViewById<RecyclerView>(R.id.postbox_recyclerView)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)


        return view
    }


}