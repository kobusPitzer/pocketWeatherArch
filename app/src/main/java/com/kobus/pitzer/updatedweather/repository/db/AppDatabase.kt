package com.kobus.pitzer.updatedweather.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kobus.pitzer.updatedweather.repository.dao.WeatherDao
import com.kobus.pitzer.updatedweather.repository.db.converter.*
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall

@Database(
    entities = [
        WeatherOneCall::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    JsonArrayConverter::class,
    DateTypeConverter::class,
    CloudsConverter::class,
    CoordConverter::class,
    CurrentConverter::class,
    DailyConverter::class,
    MainConverter::class,
    SysConverter::class,
    WeatherXConverter::class,
    WindConverter::class

)

abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}