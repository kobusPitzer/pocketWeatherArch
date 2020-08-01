package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.WeatherX

class WeatherXConverter {
    @TypeConverter
    fun stringToList(json: String?): List<WeatherX>? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<List<WeatherX>>() {

            }.type
            Gson().fromJson<List<WeatherX>>(json, type)
        }
    }

    @TypeConverter
    fun listToString(list: List<WeatherX>?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<List<WeatherX>>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}