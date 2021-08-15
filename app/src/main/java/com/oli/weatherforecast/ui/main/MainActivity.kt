package com.oli.weatherforecast.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.oli.weatherforecast.R
import com.oli.weatherforecast.data.model.Daily
import com.oli.weatherforecast.data.model.Forecast
import com.oli.weatherforecast.data.model.Hourly
import com.oli.weatherforecast.databinding.ActivityMainBinding
import com.oli.weatherforecast.ui.adapter.DailyAdapter
import com.oli.weatherforecast.ui.adapter.HourlyAdapter
import com.oli.weatherforecast.ui.details.DetailsActivity
import com.oli.weatherforecast.ui.details.DetailsViewModel
import com.oli.weatherforecast.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST = 1001

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModel()
    private val detailVM: DetailsViewModel by viewModel()

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (checkLocationPermission()) getLocation()
        else requestLocationPermission()

        lifecycleScope.launchWhenCreated { observeState() }
        swipeToRefresh()
    }

    private fun checkLocationPermission(): Boolean = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST
        )
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.lastLocation?.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                var lat = it.result.latitude
                var lon = it.result.longitude
                vm.setCoordinates(lat, lon)
                vm.getAddress(applicationContext)
                vm.getForecast()
            }
        }
    }

    private suspend fun observeState() = withContext(Dispatchers.Main) {
        vm.state.collect { state ->
            when (state) {
                is State.Loading -> showLoader(true)
                is State.Success -> {
                    showLoader(false)
                    showEmptyState(false)
                    binding.swipeRefresh.isEnabled = true

                    launch { onForecast() }
                    launch { showAddress() }
                }
                is State.Error -> onError()
            }
        }
    }

    private fun showLoader(boolean: Boolean) {
        binding.loaderCon.loader.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    private suspend fun onForecast() = withContext(Dispatchers.Main) {
        vm.forecastList.collect {
            it?.let { showDetails(it) }
        }
    }

    private suspend fun showAddress() {
        vm.address.collect {
            binding.address.text = it
        }
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getLocation()
            lifecycleScope.launch { observeState() }
        }
    }

    private fun showDetails(forecast: Forecast) {
        forecast?.let {

            Log.d("showDetails", it.toString())

            binding.temp.text =
                "${(forecast.current?.temp!!).roundToInt()}${Constants.DEGREE_SYMBOL}"

            if (it.current?.weather != null) {
                binding.weather.text =
                    forecast.current!!.weather[0]!!.description!!

                var icon = forecast.current!!.weather[0]!!.icon
                Glide
                    .with(this)
                    .load(Constants.ICON_URL + icon + ".png")
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(binding.ivWeather)
            }

            var sunrise = it.current?.sunrise
            var sunset = it.current?.sunset
            binding.txtSunrise.text = TimeUtil.epochToSimpleTime(sunrise!!)
            binding.txtSunset.text = TimeUtil.epochToSimpleTime(sunset!!)

            it.hourly?.let { showHourlyWeather(it) }
            it.daily?.let { showDailyWeather(it) }

            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showHourlyWeather(list: List<Hourly?>) {
        binding.listHourly.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = HourlyAdapter(list)
        binding.listHourly.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showDailyWeather(list: List<Daily?>) {
        binding.listDaily.layoutManager =
            LinearLayoutManager(this)

        val adapter = DailyAdapter(list)
        binding.listDaily.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.listDaily.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                list[position]?.let {
                    detailVM.setDetails(it)
                    DetailsActivity.start(view.context)
                }
            }
        })
    }

    private fun onError() {
        binding.swipeRefresh.isEnabled = true
        showLoader(false)
        showEmptyState(true)
    }

    private fun showPermissionRequiredState() {
        showLoader(false)
        showEmptyState(true)
        binding.swipeRefresh.isEnabled = false
        binding.emptyStateTitle.text = getString(R.string.permission_denied)
        binding.emptyStateMsg.text = getString(R.string.permission_denied_msg)
    }

    private fun showEmptyState(boolean: Boolean) {
        binding.emptyStateView.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) getLocation()
        else showPermissionRequiredState()
    }
}