package com.kobus.pitzer.updatedweather.repository.models

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all") var all: Int? = null
)