package com.oli.weatherforecast.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oli.weatherforecast.data.model.Daily
import com.oli.weatherforecast.databinding.ActivityDetailsBinding
import com.oli.weatherforecast.util.Constants
import com.oli.weatherforecast.util.TimeUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private val vm: DetailsViewModel by viewModel()

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launchWhenCreated {
            vm.getDetails()?.let { showDetails(it) }
        }
    }

    private fun showDetails(daily: Daily) {
        var sunrise = daily?.sunrise
        var sunset = daily?.sunset

        binding.day.text = TimeUtil.epochToDay(daily.datetime!!)
        binding.txtSunrise.text = "Sunrise\n${TimeUtil.epochToSimpleTime(sunrise!!)}"
        binding.txtSunset.text = "Sunset\n${TimeUtil.epochToSimpleTime(sunset!!)}"
        binding.feelsLike.text =
            "Feels Like\n${daily.feelsLike!!.day.toInt()}${Constants.DEGREE_SYMBOL}"
        binding.humidity.text = "Humidity\n${daily.humidity} %"
        binding.pressure.text = "Pressure\n${daily.pressure} hPa"
        binding.uvi.text = "UV Index\n${daily.uvi}"

        val min = "${daily.temp!!.min!!.toInt()}${Constants.DEGREE_SYMBOL}"
        val max = "${daily.temp!!.max!!.toInt()}${Constants.DEGREE_SYMBOL}"

        binding.temp.text = "H:$max   L:$min"

        if (daily?.weather != null) {
            binding.weather.text =
                daily!!.weather[0]!!.description!!

            var icon = daily!!.weather[0]!!.icon
            Glide
                .with(this)
                .load(Constants.ICON_URL + icon + "@2x.png")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(binding.ivWeather)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DetailsActivity::class.java)
            context.startActivity(intent)
        }
    }
}