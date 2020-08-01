package com.kobus.pitzer.updatedweather.repository.network

import androidx.lifecycle.LiveData
import com.kobus.pitzer.updatedweather.BuildConfig
import com.kobus.pitzer.updatedweather.repository.models.Weather
import com.kobus.pitzer.updatedweather.repository.models.WeatherForecast
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather?appid=" + BuildConfig.WEATHER_API_KEY + "&units=metric")
    fun getLocationWeather(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): LiveData<ApiResponse<Weather>>

    @GET("forecast?appid=" + BuildConfig.WEATHER_API_KEY + "&units=metric")
    fun getLocationWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): LiveData<ApiResponse<WeatherForecast>>

    // onecall is better to use as it has better data for display purposes
    @GET("onecall?exclude=hourly,minutely&appid=" + BuildConfig.WEATHER_API_KEY + "&units=metric")
    fun getLocationWeatherDaily(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): LiveData<ApiResponse<WeatherOneCall>>
}