package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("base") var base: String? = null,
    @SerializedName("clouds") var clouds: Clouds? = null,
    @SerializedName("cod") var cod: Int? = null,
    @SerializedName("coord") var coord: Coord? = null,
    @SerializedName("dt") var dt: Long? = null,
    @SerializedName("main") var main: Main? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("sys") var sys: Sys? = null,
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("weather") var weatherX: List<WeatherX>? = null,
    @SerializedName("wind") var wind: Wind? = null
)