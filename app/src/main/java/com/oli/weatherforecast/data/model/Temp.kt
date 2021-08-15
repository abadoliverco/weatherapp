package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "min")
    var min: Double,

    @Json(name = "max")
    var max: Double,
)