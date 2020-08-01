package com.kobus.pitzer.updatedweather.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object CorePreferences {
    private const val CORE_SHARED_PREFERENCES = "CORE_SHARED_PREFERENCES"

    private const val KEY_LAST_LAT = "KEY_LAST_LAT"
    private const val KEY_LAST_LONG = "KEY_LAST_LONG"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(appContext: Context) {
        sharedPreferences =
            appContext.getSharedPreferences(CORE_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getBaseUrl(): String {
        return "http://api.openweathermap.org/data/2.5/"
    }

    fun setLastLat(lat: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_LAT, lat)
        }
    }

    fun getLastLat(): String {
        return sharedPreferences.getString(KEY_LAST_LAT, "-25.67") ?: "-25.67"
    }

    fun setLastLong(long: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_LONG, long)
        }
    }

    fun getLastLong(): String {
        return sharedPreferences.getString(KEY_LAST_LONG, "28.67") ?: "28.67"
    }
}