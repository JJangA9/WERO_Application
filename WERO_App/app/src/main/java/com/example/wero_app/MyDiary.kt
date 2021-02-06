package com.example.wero_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MyDiary : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_diary,container,false)
        val items = mutableListOf<ListViewItem>()
        val listView = view.findViewById<ListView>(R.id.list_view)
        items.add(ListViewItem("21.02.06", "간다ㅏ라마바사아자차캌타파하"))
        items.add(ListViewItem("21.02.04", "이잉 ㅡㅠ "))

        val adapter = ListViewAdapter(items)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = parent.getItemAtPosition(position) as ListViewItem
        }

        return view
    }


}