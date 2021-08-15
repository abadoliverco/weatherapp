package com.oli.weatherforecast

import com.oli.weatherforecast.data.api.ApiService
import com.oli.weatherforecast.data.api.Client
import com.oli.weatherforecast.data.repo.MainRepository
import com.oli.weatherforecast.ui.SplashViewModel
import com.oli.weatherforecast.ui.details.DetailsViewModel
import com.oli.weatherforecast.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    single<Client> { Client() }
    single<ApiService> { get<Client>().service }
    single<DetailsViewModel> { DetailsViewModel() }

    factory<MainRepository> { MainRepository(get()) }

    viewModel<MainViewModel> { MainViewModel(get()) }
    viewModel<SplashViewModel> { SplashViewModel() }
}