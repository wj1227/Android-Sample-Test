package com.jay.androidallsampletest

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MaterialCalendarViewActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var linearLayout: LinearLayout
    private lateinit var events: MutableList<EventDay>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_calendar_view)

        initView()
        initRangeMonth()
        initDotList()
        initInvisibleDays()
        initCalendarClickListener()
    }

    private fun initView() {
        linearLayout = findViewById(R.id.linear_layout)
        calendarView = findViewById(R.id.calendar_view)
        events = mutableListOf()
    }

    private fun initDotList() {
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.DAY_OF_MONTH, 3)

        val calendar2 = Calendar.getInstance()
        calendar2.add(Calendar.DAY_OF_MONTH, 5)

        val calendar3 = Calendar.getInstance()
        calendar3.add(Calendar.DAY_OF_MONTH, 7)

        events.add(EventDay(calendar1, R.drawable.sample_icon_1))
        events.add(EventDay(calendar2, R.drawable.sample_icon_2))
        events.add(EventDay(calendar3, R.drawable.sample_icon_3))

        calendarView.setEvents(events)
    }

    private fun initRangeMonth() {
        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, -1)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 1)

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)
    }

    private fun initInvisibleDays() {
        val calendars = mutableListOf<Calendar>()

        val first = Calendar.getInstance()
        first.add(Calendar.DAY_OF_MONTH, 1)

        val second = Calendar.getInstance()
        second.add(Calendar.DAY_OF_MONTH, 4)

        val third = Calendar.getInstance()
        third.add(Calendar.DAY_OF_MONTH, 6)

        val fourth = Calendar.getInstance()
        fourth.add(Calendar.DAY_OF_MONTH, 12)

        val fifth = Calendar.getInstance()
        fifth.add(Calendar.DAY_OF_MONTH, 15)

        val final = Calendar.getInstance()
        final.add(Calendar.DAY_OF_MONTH, 27)

        calendars.add(first)
        calendars.add(second)
        calendars.add(third)
        calendars.add(fourth)
        calendars.add(fifth)
        calendars.add(final)

        calendarView.setDisabledDays(calendars)
    }

    private fun initCalendarClickListener() {
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val day = "${eventDay.calendar[Calendar.YEAR]}년 " +
                        "${eventDay.calendar[Calendar.MONTH].plus(1)}월 " +
                        "${eventDay.calendar[Calendar.DAY_OF_MONTH]}일"
                Snackbar.make(linearLayout, day, 700).also { snackbar ->
                    snackbar.setAction("확인") { snackbar.dismiss() }
                }.show()
            }
        })
    }

}