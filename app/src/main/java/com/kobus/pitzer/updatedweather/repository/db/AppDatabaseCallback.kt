package com.kobus.pitzer.updatedweather.repository.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kobus.pitzer.updatedweather.repository.preferences.CachePreference

class AppDatabaseCallback(val context: Context) : RoomDatabase.Callback() {
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)

        val lastMigration: Int? = CachePreference.getLastChannelDbVersion(context)
        if (lastMigration == null ||
            lastMigration < db.version
        ) {
            CachePreference.setLastChannelDbVersion(context, db.version)

        }
    }
}