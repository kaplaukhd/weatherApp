package com.kaplaukhd.weather.ui

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.databinding.ActivityMainBinding
import com.kaplaukhd.weather.model.Daily
import com.kaplaukhd.weather.model.Hourly
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.ui.adapter.DailyWeatherViewHolder
import com.kaplaukhd.weather.ui.adapter.HourlyWeatherViewHolder
import com.kaplaukhd.weather.ui.viewmodels.MainViewModel
import com.kaplaukhd.weather.utils.RequestPermission
import java.util.*
import kotlin.math.roundToInt


//private val Context.appComponent: AppComponent
//    get() = when(this){
//        is App -> appComponent
//        else -> this.applicationContext.appComponent
//    }

class MainActivity : AppCompatActivity() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = requireNotNull(_binding)
    private val model: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        RequestPermission.checkPermission(this)

        model.currentDate.observe(this) {
            binding.included.dateTxt.text = it
        }

        model.weather.observe(this) {
            binding.included.tempTxt.text = it.current.temp.roundToInt().toString()
            setWeatherImg(binding.included.maimImg, it.current.weather[0].main)
            binding.includedWidgets.widgetHumidityTxt.text =
                it.current.humidity.toString().plus("%")
            binding.includedWidgets.widgetThermometerTxt.text =
                it.current.pressure.toString().plus(" mBar")
            binding.includedWidgets.widgetUltravioletTxt.text = it.current.uvi.toString()
            binding.includedWidgets.widgetWindTxt.text =
                it.current.wind_speed.toString().plus(" ????/??")
            binding.included.weatherInfo.text = it.current.weather[0].description
            setRecycler(it)
        }

    }

    private fun setRecycler(weather: WeatherApiResponse) {
        val hourlyWeather = arrayListOf<Hourly>()
        val dailyWeather = arrayListOf<Daily>()
        var x = 0

        while (x <= 24) {
            hourlyWeather.add(weather.hourly[x])
            x++
        }
        x = 0
        while (x <= 2) {
            dailyWeather.add(weather.daily[x])
            x++
        }

        val hourlyDataSource = dataSourceOf(hourlyWeather)
        val dailyDataSource = dataSourceOf(dailyWeather)

        binding.hourlyRecycler.setup {
            withLayoutManager(
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            )
            withDataSource(hourlyDataSource)
            withItem<Hourly, HourlyWeatherViewHolder>(R.layout.hourly_wether_item) {
                onBind(::HourlyWeatherViewHolder) { index, item ->
                    temp.text = item.temp.roundToInt().toString().plus("??")
//                    date.text = model.getTime(item.dt.toString())
                    setWeatherImg(img, hourlyWeather[index].weather[0].main)
                }
            }
        }
        binding.dailyRecycler.setup {
            withLayoutManager(LinearLayoutManager(applicationContext))
            withDataSource(dailyDataSource)
            withItem<Daily, DailyWeatherViewHolder>(R.layout.daily_weather_item) {
                onBind(::DailyWeatherViewHolder) { index, item ->
                    val maxTemp = item.temp.max.roundToInt().toString().plus("??/")
                    val minTemp = item.temp.min.roundToInt().toString().plus("??")
                    val aboutDay = item.weather[0].description
                    temp.text = maxTemp.plus(minTemp)
                    date.text = model.getDateTime(item.dt.toString()).plus(" $aboutDay")
                    setWeatherImg(img, dailyWeather[index].weather[0].main)
                }
            }
        }
    }


    private fun setWeatherImg(img: ImageView, weather: String) {
        when (weather) {
            "Rain" -> {
                img.setImageResource(R.drawable.rainy)
            }
            "Clouds" -> {
                img.setImageResource(R.drawable.cloudy)
            }
            "Clear" -> {
                img.setImageResource(R.drawable.sun)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                binding.toolbar.title =
                    geocoder.getFromLocation(it.latitude, it.longitude, 1)[0].locality
                model.getWeather(it.latitude, it.longitude)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

