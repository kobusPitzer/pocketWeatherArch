package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like") var feels_like: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("temp_max") var temp_max: Double? = null,
    @SerializedName("temp_min") var temp_min: Double? = null
)