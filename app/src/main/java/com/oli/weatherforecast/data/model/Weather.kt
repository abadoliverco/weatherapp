package com.oli.weatherforecast.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "main")
    var main: String?,

    @Json(name = "description")
    var description: String?,

    @Json(name = "icon")
    var icon: String?
)