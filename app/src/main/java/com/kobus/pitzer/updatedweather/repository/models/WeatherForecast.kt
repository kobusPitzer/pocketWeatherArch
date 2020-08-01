package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("city") var city: City? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("list") var list: List<Weather>? = null,
    @SerializedName("message") var message: Int? = null
)