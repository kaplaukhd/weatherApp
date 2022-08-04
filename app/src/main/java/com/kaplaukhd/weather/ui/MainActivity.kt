package com.kaplaukhd.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.App
import com.kaplaukhd.weather.data.AppComponent
import com.kaplaukhd.weather.databinding.ActivityMainBinding
import com.kaplaukhd.weather.model.Result
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.ui.adapter.DailyWeatherAdapter
import com.kaplaukhd.weather.ui.adapter.HourlyWeatherAdapter
import com.kaplaukhd.weather.ui.viewmodels.MainViewModel
import com.kaplaukhd.weather.ui.viewmodels.ViewModelFactory
import com.kaplaukhd.weather.utils.RequestPermission
import com.kaplaukhd.weather.utils.WeatherImage
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


private val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var model: MainViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = requireNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        RequestPermission.checkPermission(this)
        init()
        with(binding) {
            hourlyRecycler.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            dailyRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        appComponent.inject(this)
        model = ViewModelProvider(this, factory)[MainViewModel::class.java]
        model.weather.observe(this) {
            when (it) {
                is Result.Success -> {
                    with(binding) {
                        hourlyRecycler.adapter =
                            HourlyWeatherAdapter(it.data!!)
                        dailyRecycler.adapter =
                            DailyWeatherAdapter(it.data)
                    }
                    setWeather(it.data!!)
                }
                else -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private fun setWeather(it: WeatherApiResponse) {
        with(binding) {
            included.maimImg.setImageResource(WeatherImage.getImage(it.current.weather[0].description))
            included.tempTxt.text = it.current.temp.roundToInt().toString()
            included.weatherInfo.text = it.current.weather[0].description
            with(includedWidgets) {
                widgetHumidityTxt.text =
                    it.current.humidity.toString().plus("%")
                widgetThermometerTxt.text =
                    it.current.pressure.toString().plus(" mBar")
                widgetUltravioletTxt.text = it.current.uvi.toString()
                widgetWindTxt.text =
                    it.current.wind_speed.toString().plus(" Км/ч")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun init() {
        binding.included.dateTxt.text =
            SimpleDateFormat("d LLLL", Locale.getDefault()).format(Date())
        LocationServices.getFusedLocationProviderClient(this)
            .lastLocation
            .addOnSuccessListener {
                model.location = listOf(it.latitude.toString(), it.longitude.toString())
                binding.toolbar.title =
                    Geocoder(this, Locale.getDefault())
                        .getFromLocation(it.latitude, it.longitude, 1)[0].locality
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

