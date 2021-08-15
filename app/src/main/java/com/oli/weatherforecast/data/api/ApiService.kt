package com.oli.weatherforecast.data.api

import com.oli.weatherforecast.data.model.Forecast
import com.oli.weatherforecast.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("onecall?")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude : String = "minutely",
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = Constants.API_KEY
    ): Forecast
}