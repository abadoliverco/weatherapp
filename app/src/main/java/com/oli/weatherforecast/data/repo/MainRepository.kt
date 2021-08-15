package com.oli.weatherforecast.data.repo

import android.content.Context
import android.location.Geocoder
import com.oli.weatherforecast.data.api.ApiService
import java.util.*

class MainRepository(private val apiService: ApiService) {

    var lat: Double = 0.0
    var lon: Double = 0.0

    suspend fun getForecast() = apiService.getForecast(lat, lon)

    fun getAddress(context: Context): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val local = geocoder.getFromLocation(lat, lon, 1)
            local[0].locality
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}