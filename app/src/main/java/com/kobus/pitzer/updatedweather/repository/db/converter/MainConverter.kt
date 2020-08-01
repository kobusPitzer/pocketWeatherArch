package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Main

class MainConverter {
    @TypeConverter
    fun stringToItem(json: String?): Main? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Main>() {

            }.type
            Gson().fromJson<Main>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Main?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Main>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}