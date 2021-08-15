package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Daily(
    @Json(name = "dt")
    var datetime: Long?,

    @Json(name = "sunrise")
    var sunrise: Long?,

    @Json(name = "sunset")
    var sunset: Long?,

    @Json(name = "temp")
    var temp: Temp?,

    @Json(name = "feels_like")
    var feelsLike: FeelsLike?,

    @Json(name = "weather")
    var weather: List<Weather?>,

    @Json(name = "humidity")
    var humidity: Int,

    @Json(name = "pressure")
    var pressure: Int,

    @Json(name = "uvi")
    var uvi: Double


    )