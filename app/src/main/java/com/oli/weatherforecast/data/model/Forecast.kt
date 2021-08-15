package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "lat")
    var lat: Double?,

    @Json(name = "lon")
    var lon: Double?,

    @Json(name = "timezone")
    var timezone: String?,

    @Json(name = "current")
    var current: Current?,

    @Json(name = "hourly")
    var hourly: List<Hourly?>,

    @Json(name = "daily")
    var daily: List<Daily?>
)