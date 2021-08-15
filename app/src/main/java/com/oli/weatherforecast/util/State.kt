package com.oli.weatherforecast.util

sealed class State {
    abstract val state: Int

    data class Loading(
        override val state: Int = 0x003
    ) : State()

    data class Success(
        override val state: Int = 0x001
    ) : State()

    data class Error(
        override val state: Int = 0x000
    ) : State()
}
