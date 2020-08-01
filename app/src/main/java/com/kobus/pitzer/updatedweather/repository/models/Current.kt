package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("dew_point") var dew_point: Double? = null,
    @SerializedName("dt") var dt: Long? = null,
    @SerializedName("feels_like") var feels_like: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("uvi") var uvi: Double? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("weather") var weather: List<WeatherX>? = null,
    @SerializedName("wind_deg") var wind_deg: Double? = null,
    @SerializedName("wind_gust") var wind_gust: Double? = null,
    @SerializedName("wind_speed") var wind_speed: Double? = null
)