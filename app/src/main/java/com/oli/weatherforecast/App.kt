package com.oli.weatherforecast

import android.app.Application
import com.squareup.moshi.Moshi
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {

    private val moduleList: List<Module> = listOf(
        appModule
    )

    override fun onCreate() {
        super.onCreate()
        Moshi.Builder().build()

        startKoin {
            this
            modules(moduleList)
        }
    }
}