package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "dt")
    var datetime: Long?,

    @Json(name = "temp")
    var temp: Double?,

    @Json(name = "feels_like")
    var feelsLike: Double?,

    @Json(name = "humidity")
    var humidity: Int?,

    @Json(name = "pressure")
    var pressure: Int?,

    @Json(name = "wind_speed")
    var windSpeed: Double?,

    @Json(name = "weather")
    var weather: List<Weather?>
)