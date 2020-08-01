package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Daily

class DailyConverter {
    @TypeConverter
    fun stringToList(json: String?): List<Daily>? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<List<Daily>>() {

            }.type
            Gson().fromJson<List<Daily>>(json, type)
        }
    }

    @TypeConverter
    fun listToString(list: List<Daily>?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<List<Daily>>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}