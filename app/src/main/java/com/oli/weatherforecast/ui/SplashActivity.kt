package com.oli.weatherforecast.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.oli.weatherforecast.R
import com.oli.weatherforecast.ui.main.MainActivity
import com.oli.weatherforecast.util.NetworkUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val vm: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(2000L)

            when (NetworkUtil.isConnected(this@SplashActivity)) {
                true ->  MainActivity.start(this@SplashActivity)
                else -> showNetworkError()
            }
        }
    }

    private fun showNetworkError() {
        val dialog = MaterialAlertDialogBuilder(this)
        dialog.setTitle("Network Error!")
        dialog.setMessage("Please check your network connection and try again later.")
        dialog.setCancelable(false)
        dialog.setPositiveButton(
            "Okay"
        ) { dialog, which ->
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }
}