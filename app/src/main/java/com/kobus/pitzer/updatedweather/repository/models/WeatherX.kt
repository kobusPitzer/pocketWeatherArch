package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class WeatherX(
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null
)