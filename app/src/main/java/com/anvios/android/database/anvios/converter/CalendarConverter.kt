package com.anvios.android.database.anvios.converter

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun toCalendar(timeInMillis: Long?): Calendar? {
        return if (timeInMillis != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMillis
            calendar
        } else {
            null
        }
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}