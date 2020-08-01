package com.kobus.pitzer.updatedweather.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall

@Dao
interface WeatherDao {
    @Query("select * from WeatherOneCall")
    fun getWeatherOneCallData(): LiveData<WeatherOneCall?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeatherOneCallData(data: WeatherOneCall)

    @Transaction
    fun addWeatherData(data: WeatherOneCall) {
        addWeatherOneCallData(data)
    }

}