package com.kobus.pitzer.updatedweather.repository.resource

import androidx.lifecycle.LiveData

import com.kobus.pitzer.updatedweather.repository.core.AppExecutors
import com.kobus.pitzer.updatedweather.repository.models.Weather
import com.kobus.pitzer.updatedweather.repository.network.ApiResponse
import com.kobus.pitzer.updatedweather.repository.network.WeatherService
import timber.log.Timber

class GetLocationWeatherResource(
    appExecutors: AppExecutors,
    weatherService: WeatherService,
    lat: String,
    long: String
) {
    private var networkResource: NetworkResource<Weather> =
        object : NetworkResource<Weather>(appExecutors) {

            override fun processCallResult(item: Weather?) {
                Timber.i("Successfully got weather")
            }

            override fun createCall(): LiveData<ApiResponse<Weather>> {
                return weatherService.getLocationWeather(lat, long)
            }
        }

    fun getResult(): LiveData<Resource<Weather>> {
        return networkResource.asLiveData()
    }
}