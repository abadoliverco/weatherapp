package com.oli.weatherforecast.ui.details

import androidx.lifecycle.ViewModel
import com.oli.weatherforecast.data.model.Daily
import kotlinx.coroutines.flow.MutableStateFlow

class DetailsViewModel : ViewModel() {

    private val _details = MutableStateFlow<Daily?>(null)

    fun setDetails(daily: Daily?) {
        _details.value = daily
    }

    fun getDetails() = _details.value
}