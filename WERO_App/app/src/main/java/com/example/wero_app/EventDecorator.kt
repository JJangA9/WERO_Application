package com.example.wero_app

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(color : Int, context: Activity?, date : Collection<CalendarDay>) : DayViewDecorator {
    var dotColor = color
    var dates = HashSet<CalendarDay>(date)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        //view.setSelectionDrawable(drawable!!)
        view.addSpan(DotSpan(dotColor))
    }

    init {
        // You can set background for Decorator via drawable here
    }
}