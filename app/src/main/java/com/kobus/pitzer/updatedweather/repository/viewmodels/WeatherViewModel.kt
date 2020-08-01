package com.kobus.pitzer.updatedweather.repository.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kobus.pitzer.updatedweather.repository.WeatherRepository
import com.kobus.pitzer.updatedweather.repository.constant.Status
import com.kobus.pitzer.updatedweather.repository.constant.WeatherTypes
import com.kobus.pitzer.updatedweather.repository.models.Current
import com.kobus.pitzer.updatedweather.repository.models.Weather
import com.kobus.pitzer.updatedweather.repository.models.WeatherForecast
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import com.kobus.pitzer.updatedweather.repository.preferences.CorePreferences
import com.kobus.pitzer.updatedweather.repository.resource.Resource
import kotlin.math.floor

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val degreeText = "\u00B0"
    var lat: String = CorePreferences.getLastLat()
    var long: String = CorePreferences.getLastLong()
    internal var currentDailyForecastWeather: LiveData<Resource<WeatherOneCall?>> =
        MediatorLiveData()
    private var main: MediatorLiveData<Current> = MediatorLiveData()

    var currentTemp: MediatorLiveData<String> = MediatorLiveData()
    var currentTempText: MediatorLiveData<String> = MediatorLiveData()
    var minTempText: MediatorLiveData<String> = MediatorLiveData()
    var maxTempText: MediatorLiveData<String> = MediatorLiveData()
    var currentWeatherDescription: MediatorLiveData<String> = MediatorLiveData()
    var weatherType: MediatorLiveData<WeatherTypes> = MediatorLiveData()

    init {
        postInitialValues()
    }

    fun getLocationWeather() {
        currentDailyForecastWeather = weatherRepository.getWeatherForecastDaily(lat, long)

        addSources()
    }

    private fun addSources() {
        currentWeatherDescription.addSource(currentDailyForecastWeather) { weatherResource ->
            if (weatherResource?.status == Status.SUCCESS || weatherResource?.status == Status.CACHE) {
                currentWeatherDescription.postValue(weatherResource.data?.current?.weather?.first()?.description)
            }
        }

        weatherType.addSource(currentDailyForecastWeather) { weatherResource ->
            if (weatherResource?.status == Status.SUCCESS || weatherResource?.status == Status.CACHE) {
                weatherType.postValue(getWeatherTypeBasedOnID(weatherResource.data?.current?.weather?.first()?.id))
            }
        }

        minTempText.addSource(currentDailyForecastWeather) { mainWeatherDate ->
            minTempText.postValue(mainWeatherDate?.data?.daily?.first()?.temp?.min?.let {
                floor(it).toInt().toString() + degreeText + "\nmin"
            })
        }

        maxTempText.addSource(currentDailyForecastWeather) { mainWeatherDate ->
            maxTempText.postValue(mainWeatherDate?.data?.daily?.first()?.temp?.max?.let {
                floor(it).toInt().toString() + degreeText + "\nmax"
            })
        }

        main.addSource(currentDailyForecastWeather) { weatherResource ->
            if (weatherResource?.status == Status.SUCCESS || weatherResource?.status == Status.CACHE) {
                val mainData = weatherResource.data?.current
                main.postValue(mainData)
            }
        }

        currentTemp.addSource(currentDailyForecastWeather) { weatherResource ->
            currentTemp.postValue(weatherResource.data?.current?.temp?.let {
                floor(it).toInt().toString() + degreeText
            })
            currentTempText.postValue(weatherResource.data?.current?.temp?.let {
                floor(it).toInt().toString() + degreeText + "\ncurrent"
            })
        }
    }

    // Normalizing value based on different codes
    // https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
    private fun getWeatherTypeBasedOnID(id: Int?): WeatherTypes? {
        when (id) {
            in 200..622 -> return WeatherTypes.RAINY
            800 -> return WeatherTypes.SUNNY
            in 801..805 -> return WeatherTypes.CLOUDY
            else -> return WeatherTypes.SUNNY
        }
    }

    private fun postInitialValues() {
        currentTemp.postValue("~")
        currentTempText.postValue("~")
        minTempText.postValue("~")
        maxTempText.postValue("~")
        currentWeatherDescription.postValue("")

        weatherType.postValue(WeatherTypes.SUNNY)
    }

    // Gets weather forecast for 5 days based on specific location
    fun getWeatherForecast(lat: String, long: String): LiveData<Resource<WeatherForecast>> {
        this.long = long
        this.lat = lat

        return weatherRepository.getWeatherForecast(lat, long)
    }

    // Gets weather based on specific location
    fun getLocationWeather(lat: String, long: String): LiveData<Resource<Weather>> {
        this.long = long
        this.lat = lat

        return weatherRepository.getCurrentWeather(lat, long)
    }
}