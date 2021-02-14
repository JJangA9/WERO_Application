package com.example.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity

class MyDiaryAdapter(val context: Context, val diaryRecycler : ArrayList<MyDiaryRecyclerViewItem>) : RecyclerView.Adapter<MyDiaryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_diary_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryRecycler.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val listener = View.OnClickListener {
            //(getActivity(context) as MainActivity).changeFragment2()
            (getActivity(context) as MainActivity).changeFragmentHasBackStack(R.id.my_diary, DiaryPage())
        }
        holder?.bind(listener, diaryRecycler[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date = itemView.findViewById<TextView>(R.id.txt_date)
        private val diary = itemView.findViewById<TextView>(R.id.txt_content)

        fun bind(listener: View.OnClickListener, diaryItem: MyDiaryRecyclerViewItem, context: Context) {
            date.text = diaryItem.date
            diary.text = diaryItem.letter
            itemView.setOnClickListener(listener)
        }
    }

}

class MyDiaryRecyclerViewItem(val date: String, val letter: String) {
}