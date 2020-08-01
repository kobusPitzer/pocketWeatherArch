package com.kobus.pitzer.updatedweather.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.updatedweather.repository.models.Weather
import com.kobus.pitzer.updatedweather.repository.models.WeatherForecast
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import com.kobus.pitzer.updatedweather.repository.resource.Resource

interface WeatherRepository {
    fun getCurrentWeather(lat: String, long: String): LiveData<Resource<Weather>>
    fun getWeatherForecast(lat: String, long: String): LiveData<Resource<WeatherForecast>>
    fun getWeatherForecastDaily(lat: String, long: String): LiveData<Resource<WeatherOneCall?>>
}