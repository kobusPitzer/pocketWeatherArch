package com.kobus.pitzer.updatedweather.repository.preferences

import android.content.Context
import android.content.SharedPreferences

object CachePreference {
    private const val SHARED_PREFERENCE_NAME = "cache_preference"

    private fun getSharedPreferences(context: Context)
            : SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setLastChannelDbVersion(context: Context, version: Int?) {
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("last_channel_db_version", version ?: -1)
        editor.apply()
    }

    fun getLastChannelDbVersion(context: Context): Int? {
        return getSharedPreferences(context).getInt("last_channel_db_version", -1)
    }
}