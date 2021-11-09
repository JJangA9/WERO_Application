package com.wero.wero_app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDiaryCalendar : Fragment() {

    lateinit var sheetBehavior:BottomSheetBehavior<LinearLayout>
    lateinit var mcontext: Context
    private var diaryList = arrayListOf<DiaryItem>()
    private var diaryOneDateList = arrayListOf<DiaryItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.my_diary_calendar, container, false)

        // List button listener
        val listBtn = view.findViewById<ImageButton>(R.id.imgbtn_list)
        listBtn.setOnClickListener {
            (activity as MainActivity).changeFragmentNoBackStack(R.id.my_diary, MyDiary())
        }

        // Bottom sheet
        val bottomSheet: LinearLayout by lazy { view.findViewById<LinearLayout>(R.id.bottom_sheet) }
        val txtDate = view.findViewById<TextView>(R.id.txt_date)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetMoveAction()

        // Calendar view
        val calendarView = view.findViewById<MaterialCalendarView>(R.id.calendar)
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY) // 일요일부터 시작
            .setCalendarDisplayMode(CalendarMode.MONTHS) // 월별로 표시

        // Get diary list of this month
        val date: String = SimpleDateFormat("yyyy-MM").format(Date())
        var userId: String? = null
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.d("calendar", "사용자 정보 요청 실패", error)
            }
            else {
                userId = user?.id?.toString()
                userId?.let {
                    diaryList.clear()
                    getDiaryList(it, date)
                }
            }
        }


        // Create red dot (today)
        val activity = activity as MainActivity
        //calendarView.addDecorators(EventDecorator(Color.RED, activity, dotDates()))

        // Date selected listener
        calendarView.setOnDateChangedListener { widget, selectedDate, selected ->
            Log.d("calendar", selectedDate.day.toString())
            val dateText = selectedDate.year.toString() + "." + (selectedDate.month + 1).toString() + "." + selectedDate.day.toString()
            txtDate.text = dateText
            setDiaryList(selectedDate.day)
        }

        // Month selected listener
        calendarView.setOnMonthChangedListener { widget, selectedDate ->
            val year = selectedDate.year
            val month = selectedDate.month + 1
            val dateText: String

            txtDate.text = "날짜를 선택해 주세요!"

            if(month < 10) dateText = "$year-0$month"
            else dateText = "$year-$month"
            userId?.let {
                diaryList.clear()
                getDiaryList(it, dateText)
            }
        }

        return view
    }

    private fun setRecyclerView(diaryList: ArrayList<DiaryItem>) {
        //Recycler view
        val mAdapter = MyDiaryCalendarAdapter(mcontext, diaryList)
        val mRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler_bottom)
        mRecyclerview?.adapter = mAdapter

        val lm = LinearLayoutManager(mcontext)
        mRecyclerview?.layoutManager = lm
        mRecyclerview?.setHasFixedSize(true)
    }

    //Set diary list (day)
    private fun setDiaryList(selectedDate: Int) {
        diaryOneDateList.clear()
        for(i in 0 until diaryList.size) {
            val diaryDate = diaryList[i].diaryDate
            val diaryDay = parseInt(diaryDate.substring(8, 10))
            if(diaryDay == selectedDate) {
                diaryOneDateList.add(diaryList[i])
            }
        }
        setRecyclerView(diaryOneDateList)
    }

    private fun getDiaryList(userId: String, date: String) {
        val service = (activity as MainActivity).service
        service.getDiaryList(userId, date).enqueue(object : Callback<JsonArrayResponse> {
            override fun onFailure(call: Call<JsonArrayResponse>, t: Throwable) {
                Log.d("calendar", "get failure")
            }

            override fun onResponse(call: Call<JsonArrayResponse>, response: Response<JsonArrayResponse>) {
                val list = response.body()
                val arr = list?.result
                if (list != null) {
                    Log.d("calendar", arr.toString())

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

                        //setDiaryList(SimpleDateFormat("dd").format(Date()))
                    }
                    setRecyclerView(diaryList)
                }
                else {
                    Log.d("mydiary", "null")
                }
            }
        })
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