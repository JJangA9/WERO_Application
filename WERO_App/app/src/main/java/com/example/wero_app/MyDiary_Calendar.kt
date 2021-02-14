package com.example.wero_app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.*
import kotlin.collections.ArrayList

class MyDiary_Calendar : Fragment() {

    lateinit var sheetBehavior:BottomSheetBehavior<LinearLayout>
    lateinit var mcontext: Context
    var letterList = arrayListOf<MyDiary_Calendar_RecyclerViewItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.my_diary_calendar, container, false)

        letterList = arrayListOf<MyDiary_Calendar_RecyclerViewItem>(
                MyDiary_Calendar_RecyclerViewItem("곱창 ! 껍데기 ! 닭발 ! 소주 ! \n 배고팡"),
                MyDiary_Calendar_RecyclerViewItem( "취업시켜주세요 ~ ~ ~ ~ "),
                MyDiary_Calendar_RecyclerViewItem("Congratulation 넌 참 대단해 \n"
                        + "Congratulation 어쩜 그렇게 \n"
                        + "아무렇지 않아 하며 날 짓밟아 \n웃는 얼굴을 보니 다 잊었나봐")

        )

        val mAdapter = MyDiary_CalendarAdapter(mcontext, letterList)
        val mRecyclerview = view.findViewById<RecyclerView>(R.id.bottomSheet_recyclerView)
        mRecyclerview.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview.layoutManager = lm
        mRecyclerview.setHasFixedSize(true)


        val bottomSheet: LinearLayout by lazy { view.findViewById<LinearLayout>(R.id.bottom_sheet) }
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetMoveAction()

        var calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY) // 일요일부터 시작
            .setCalendarDisplayMode(CalendarMode.MONTHS) // 월별로 표시

        val activity = getActivity() as MainActivity
        calendarView.addDecorators(EventDecorator(Color.RED, activity,  dotDates()))

        val listBtn = view.findViewById<ImageButton>(R.id.imgbtn_list)
        listBtn.setOnClickListener {
            (getActivity() as MainActivity).changeFragmentNoBackStack(R.id.my_diary, MyDiary())
        }


        return view
    }

    //bottomSheet 확장, 축소 action
    private fun bottomSheetMoveAction() {
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //슬라이드 될 때
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED-> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED-> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN-> {
                    }
                    BottomSheetBehavior.STATE_SETTLING-> {
                    }
                }
            }
        })
    }

    //점을 표시할 날짜 설정
    private fun dotDates() : ArrayList<CalendarDay> {
        val dates = ArrayList<CalendarDay>()

        var calendar : Calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)

        for(i in 1..10) {
            var day : CalendarDay = CalendarDay.from(calendar)
            dates.add(day)
            calendar.add(Calendar.DATE, 5)
        }

        return dates
    }
}