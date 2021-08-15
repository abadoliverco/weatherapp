package com.oli.weatherforecast.ui.main

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oli.weatherforecast.data.model.Forecast
import com.oli.weatherforecast.data.repo.MainRepository
import com.oli.weatherforecast.util.State
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _forecastList = MutableStateFlow<Forecast?>(null)
    val forecastList: StateFlow<Forecast?> = _forecastList

    private val _state = MutableStateFlow<State>(State.Loading())
    val state: StateFlow<State> = _state

    private val _address = MutableStateFlow<String?>(null)
    val address: StateFlow<String?> = _address

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    fun getForecast() {
        viewModelScope.launch {
            try {
                var list = async { mainRepository.getForecast() }
                _forecastList.value = list.await()
                _state.value = State.Success()
            } catch (e: Exception) {
                _state.value = State.Error()
            }
        }
    }

    fun setCoordinates(lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon
        mainRepository.lat = lat
        mainRepository.lon = lon
    }

    fun getAddress(context: Context) {
        viewModelScope.launch {
            val city = async { mainRepository.getAddress(context) }
            _address.value = city.await()
        }
    }
}