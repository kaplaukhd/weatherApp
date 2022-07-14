package com.kaplaukhd.weather.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.databinding.ActivityMainBinding
import com.kaplaukhd.weather.model.Daily
import com.kaplaukhd.weather.model.Hourly
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.ui.adapter.DailyWeatherViewHolder
import com.kaplaukhd.weather.ui.adapter.HourlyWeatherViewHolder
import com.kaplaukhd.weather.ui.viewmodels.MainViewModel
import com.kaplaukhd.weather.utils.RequestPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = requireNotNull(_binding)
    private val model: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        RequestPermission().requestPermission(this, this)
        binding.swipeLayout.setOnRefreshListener {
                model.geo()
                binding.swipeLayout.isRefreshing = false
        }
        model.geo()
        binding.swipeLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        model.currentDate.observe(this) {
            binding.included.dateTxt.text = it
        }

        model.city.observe(this) {
            binding.toolbar.title = it
        }

        model.weather.observe(this) {
            setRecycler(it)
        }


    }

    override fun onResume() {
        super.onResume()
        model.geo()
    }

    private fun setRecycler(weather: WeatherApiResponse) {
        binding.included.tempTxt.text = weather.current.temp.roundToInt().toString()
        setWeatherImg(binding.included.maimImg, weather.current.weather[0].description)
        binding.includedWidgets.widgetHumidityTxt.text =
            weather.current.humidity.toString().plus("%")
        binding.includedWidgets.widgetThermometerTxt.text =
            weather.current.pressure.toString().plus(" mBar")
        binding.includedWidgets.widgetUltravioletTxt.text = weather.current.uvi.toString()
        binding.includedWidgets.widgetWindTxt.text =
            weather.current.wind_speed.toString().plus(" Км/ч")
        binding.included.weatherInfo.text = weather.current.weather[0].description

        val hourlyWeather = arrayListOf<Hourly>()
        val dailyWeather = arrayListOf<Daily>()
        for(i in 0..24){
            hourlyWeather.add(weather.hourly[i])
        }
        for(i in 0..2){
            dailyWeather.add(weather.daily[i])
        }

        val hourlyDataSource = dataSourceOf(hourlyWeather)
        val dailyDataSource = dataSourceOf(dailyWeather)

        binding.hourlyRecycler.setup {
            withLayoutManager(LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            )
            withDataSource(hourlyDataSource)
            withItem<Hourly, HourlyWeatherViewHolder>(R.layout.hourly_weather_item) {
                onBind(::HourlyWeatherViewHolder) { index, item ->
                    temp.text = item.temp.roundToInt().toString().plus("°")
                    date.text = model.getTime(item.dt.toString())
                    setWeatherImg(img, hourlyWeather[index].weather[0].description)
                }
            }
        }
        binding.dailyRecycler.setup {
            withLayoutManager(LinearLayoutManager(applicationContext))
            withDataSource(dailyDataSource)
            withItem<Daily, DailyWeatherViewHolder>(R.layout.daily_weather_item) {
                onBind(::DailyWeatherViewHolder) { index, item ->
                    val maxTemp = item.temp.max.roundToInt().toString().plus("°/")
                    val minTemp = item.temp.min.roundToInt().toString().plus("°")
                    val aboutDay = item.weather[0].description
                    temp.text = maxTemp.plus(minTemp)
                    date.text = model.getDateTime(item.dt.toString()).plus(" $aboutDay")
                    setWeatherImg(img, dailyWeather[index].weather[0].description)
                }
            }
        }
    }


    private fun setWeatherImg(img: ImageView, weather: String) {
        when (weather) {
            "дождь" -> {
                img.setImageResource(R.drawable.heavy_rain)
            }
            "переменная облачность" -> {
                img.setImageResource(R.drawable.partly_cloudy)
            }
            "пасмурно" -> {
                img.setImageResource(R.drawable.cloudy)
            }
            "небольшой дождь" -> {
                img.setImageResource(R.drawable.rainy)
            }
            "облачно с прояснениями" -> {
                img.setImageResource(R.drawable.clouds)
            }
            "Clear" -> {
                img.setImageResource(R.drawable.sun)
            }
            "гроза" -> {
                img.setImageResource(R.drawable.storm)
            }
        }
    }


    companion object {
        const val API_KEY = "0613d06f137046da16497e676594d143"
        const val UNIT = "metric"
        const val LANG = "ru"
    }

}

