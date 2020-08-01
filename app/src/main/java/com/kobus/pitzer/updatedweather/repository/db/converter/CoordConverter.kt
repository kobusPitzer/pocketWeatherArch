package com.kobus.pitzer.updatedweather.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.updatedweather.repository.models.Coord

class CoordConverter {
    @TypeConverter
    fun stringToItem(json: String?): Coord? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Coord>() {

            }.type
            Gson().fromJson<Coord>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Coord?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Coord>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}