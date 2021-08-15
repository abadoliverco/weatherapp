package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeelsLike(
    @Json(name = "day")
    var day: Double,

    @Json(name = "night")
    var night: Double,

    @Json(name = "eve")
    var eve: Double,

    @Json(name = "morn")
    var morning: Double

)