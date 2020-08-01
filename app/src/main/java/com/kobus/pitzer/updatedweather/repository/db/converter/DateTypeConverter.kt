package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import java.util.*

// Date to Long
class DateTypeConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) {
            null
        } else {
            Date(timestamp)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time
}
