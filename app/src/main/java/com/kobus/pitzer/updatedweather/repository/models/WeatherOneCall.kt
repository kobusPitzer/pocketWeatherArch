package com.kobus.pitzer.updatedweather.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class WeatherOneCall(
    @PrimaryKey
    var id: Long = 0,
    @SerializedName("current") var current: Current? = null,
    @SerializedName("daily") var daily: List<Daily>? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_offset") var timezone_offset: Int? = null
)