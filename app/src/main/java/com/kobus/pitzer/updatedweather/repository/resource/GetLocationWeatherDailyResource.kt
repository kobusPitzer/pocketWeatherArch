package com.kobus.pitzer.updatedweather.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.updatedweather.repository.core.AppExecutors
import com.kobus.pitzer.updatedweather.repository.db.AppDatabase
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import com.kobus.pitzer.updatedweather.repository.network.ApiResponse
import com.kobus.pitzer.updatedweather.repository.network.WeatherService

class GetLocationWeatherDailyResource(
    appExecutors: AppExecutors,
    weatherService: WeatherService,
    db: AppDatabase,
    lat: String,
    long: String
) {

    private var networkBoundResource =
        object : NetworkBoundResource<WeatherOneCall?, WeatherOneCall>(appExecutors) {
            override fun saveCallResult(item: WeatherOneCall?) {
                item?.let { db.weatherDao().addWeatherData(it) }
            }

            override fun shouldFetch(data: WeatherOneCall?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<WeatherOneCall?> {
                return db.weatherDao().getWeatherOneCallData()
            }

            override fun createCall(): LiveData<ApiResponse<WeatherOneCall>> {
                return weatherService.getLocationWeatherDaily(lat, long)
            }

        }

    fun getResult(): LiveData<Resource<WeatherOneCall?>> {
        return networkBoundResource.asLiveData()
    }
}