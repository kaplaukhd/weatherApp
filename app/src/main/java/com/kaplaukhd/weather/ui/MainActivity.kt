package com.kaplaukhd.weather.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.datasource.emptyDataSource
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.databinding.ActivityMainBinding
import com.kaplaukhd.weather.model.Daily
import com.kaplaukhd.weather.model.Hourly
import com.kaplaukhd.weather.model.HourlyWeather
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.ui.adapter.DailyWeatherViewHolder
import com.kaplaukhd.weather.ui.adapter.HourlyWeatherViewHolder
import com.kaplaukhd.weather.ui.viewmodels.MainViewModel
import com.kaplaukhd.weather.utils.RequestPermission
import kotlin.math.roundToInt

lateinit var binding: ActivityMainBinding
lateinit var lm: LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.hourlyRecycler.setHasFixedSize(true)
        binding.dailyRecycler.setHasFixedSize(true)
        lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        if(!RequestPermission().checkSelfPermission(this)){
            RequestPermission().requestPermission(this, this)
        }

        binding.swipeLayout.setOnRefreshListener {
            model.geo()
            binding.swipeLayout.isRefreshing = false
        }
        model.geo()

        model.currentDate.observe(this) {
            binding.included.dateTxt.text = it
        }

        model.city.observe(this) {
            binding.toolbar.title = it
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
                it.current.wind_speed.toString().plus(" Км/ч")
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
            withLayoutManager(lm)
            withDataSource(hourlyDataSource)
            withItem<Hourly, HourlyWeatherViewHolder>(R.layout.hourly_wether_item) {
                onBind(::HourlyWeatherViewHolder) { index, item ->
                    temp.text = item.temp.roundToInt().toString().plus("°")
                    date.text = model.getTime(item.dt.toString())
                    setWeatherImg(img, hourlyWeather[index].weather[0].main)
                }
            }
        }
        binding.dailyRecycler.setup {
            withLayoutManager(LinearLayoutManager(applicationContext))
            withDataSource(dailyDataSource)
            withItem<Daily, DailyWeatherViewHolder>(R.layout.daily_weather_item) {
                onBind(::DailyWeatherViewHolder) { index, item ->
                    val MaxTemp = item.temp.max.roundToInt().toString().plus("°/")
                    val MinTemp = item.temp.min.roundToInt().toString().plus("°")
                    val aboutDay = item.weather[0].description
                    temp.text = MaxTemp.plus(MinTemp)
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


    companion object {
        const val API_KEY = "0613d06f137046da16497e676594d143"
        const val UNIT = "metric"
        const val LANG = "ru"
        const val TAG = "mainActivity"
    }

}
