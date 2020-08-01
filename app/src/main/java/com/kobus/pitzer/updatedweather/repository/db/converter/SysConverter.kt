package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Sys

class SysConverter {
    @TypeConverter
    fun stringToItem(json: String?): Sys? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Sys>() {

            }.type
            Gson().fromJson<Sys>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Sys?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Sys>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}