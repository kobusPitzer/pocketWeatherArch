package com.kobus.pitzer.updatedweather.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.updatedweather.repository.core.AppExecutors
import com.kobus.pitzer.updatedweather.repository.models.WeatherForecast
import com.kobus.pitzer.updatedweather.repository.network.ApiResponse
import com.kobus.pitzer.updatedweather.repository.network.WeatherService
import timber.log.Timber

class GetLocationWeatherForecastResource(
    appExecutors: AppExecutors,
    weatherService: WeatherService,
    lat: String,
    long: String
) {
    private var networkResource: NetworkResource<WeatherForecast> =
        object : NetworkResource<WeatherForecast>(appExecutors) {

            override fun processCallResult(item: WeatherForecast?) {
                Timber.i("Successfully got weather forecast")
            }

            override fun createCall(): LiveData<ApiResponse<WeatherForecast>> {
                return weatherService.getLocationWeatherForecast(lat, long)
            }
        }

    fun getResult(): LiveData<Resource<WeatherForecast>> {
        return networkResource.asLiveData()
    }
}