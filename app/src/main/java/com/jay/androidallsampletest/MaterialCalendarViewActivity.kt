package com.jay.androidallsampletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import java.util.*

class MaterialCalendarViewActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var events: MutableList<EventDay>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_calendar_view)

        init()
        dotEvent()
        rangeMonth()
        disableCalendar()
    }

    private fun init() {
        calendarView = findViewById(R.id.calendarView)
        events = mutableListOf()
    }

    private fun dotEvent() {
        val dot = Calendar.getInstance()
        dot.add(Calendar.DAY_OF_MONTH, 10)
        events.add(EventDay(dot, R.drawable.sample_icon_2))

        calendarView.setEvents(events)
    }

    private fun rangeMonth() {
        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, -2)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 2)

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)
    }

    private fun disableCalendar() {
        calendarView.setDisabledDays(disabledDays = disableDays())
    }

    private fun disableDays(): List<Calendar> {
        val calendars = mutableListOf<Calendar>()

        val firstDisabled = Calendar.getInstance()
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2)

        val secondDisabled = Calendar.getInstance()
        secondDisabled.add(Calendar.DAY_OF_MONTH, 5)

        val thirdDisabled = Calendar.getInstance()
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 8)

        calendars.add(firstDisabled)
        calendars.add(secondDisabled)
        calendars.add(thirdDisabled)

        return calendars
    }
}