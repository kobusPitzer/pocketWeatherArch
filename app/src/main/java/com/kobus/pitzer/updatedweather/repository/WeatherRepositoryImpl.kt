package com.kobus.pitzer.updatedweather.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.updatedweather.repository.core.AppExecutors
import com.kobus.pitzer.updatedweather.repository.db.AppDatabase
import com.kobus.pitzer.updatedweather.repository.models.Weather
import com.kobus.pitzer.updatedweather.repository.models.WeatherForecast
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import com.kobus.pitzer.updatedweather.repository.network.WeatherService
import com.kobus.pitzer.updatedweather.repository.resource.GetLocationWeatherDailyResource
import com.kobus.pitzer.updatedweather.repository.resource.GetLocationWeatherForecastResource
import com.kobus.pitzer.updatedweather.repository.resource.GetLocationWeatherResource
import com.kobus.pitzer.updatedweather.repository.resource.Resource

class WeatherRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
    private val weatherService: WeatherService
) : WeatherRepository {

    override fun getCurrentWeather(lat: String, long: String): LiveData<Resource<Weather>> {
        return GetLocationWeatherResource(
            appExecutors,
            weatherService,
            lat,
            long
        ).getResult()
    }

    override fun getWeatherForecast(
        lat: String,
        long: String
    ): LiveData<Resource<WeatherForecast>> {
        return GetLocationWeatherForecastResource(
            appExecutors,
            weatherService,
            lat,
            long
        ).getResult()
    }

    override fun getWeatherForecastDaily(
        lat: String,
        long: String
    ): LiveData<Resource<WeatherOneCall?>> {
        return GetLocationWeatherDailyResource(
            appExecutors,
            weatherService,
            appDatabase,
            lat,
            long
        ).getResult()
    }
}