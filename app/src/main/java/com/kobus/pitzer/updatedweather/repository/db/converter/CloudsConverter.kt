package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Clouds

class CloudsConverter {
    @TypeConverter
    fun stringToItem(json: String?): Clouds? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Clouds>() {

            }.type
            Gson().fromJson<Clouds>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Clouds?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Clouds>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}