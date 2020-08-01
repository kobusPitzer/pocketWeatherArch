package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Current

class CurrentConverter {
    @TypeConverter
    fun stringToItem(json: String?): Current? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Current>() {

            }.type
            Gson().fromJson<Current>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Current?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Current>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}