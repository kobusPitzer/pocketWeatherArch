package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import org.json.JSONArray

class JsonArrayConverter {
    @TypeConverter
    fun toDate(value: String?): List<String> {
        val result: MutableList<String> = mutableListOf()
        return if (value == null) {
            result
        } else {

            val jsonArray = JSONArray(value)
            for (i in 0..(jsonArray.length() - 1)) {
                result.add(jsonArray.getString(i))
            }
            result
        }
    }

    @TypeConverter
    fun toTimestamp(values:  List<String>?): String?  {
        val jsonArray = JSONArray()
        values?.forEach { jsonArray.put(it) }
        return jsonArray.toString()
    }
}