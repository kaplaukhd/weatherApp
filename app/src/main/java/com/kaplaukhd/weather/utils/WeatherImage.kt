package com.kaplaukhd.weather.utils

import com.kaplaukhd.weather.R

object WeatherImage {
    fun getImage(currentWeather: String): Int {
        var img = 0
        when (currentWeather) {
            "дождь" -> {
                img = R.drawable.heavy_rain
            }
            "ясно" -> {
                img = R.drawable.sun
            }
            "переменная облачность" -> {
                img = R.drawable.partly_cloudy
            }
            "пасмурно" -> {
                img = R.drawable.cloudy
            }
            "небольшой дождь" -> {
                img = R.drawable.rainy
            }
            "облачно с прояснениями" -> {
                img = R.drawable.clouds
            }
            "ясно" -> {
                img = R.drawable.sun
            }
            "гроза" -> {
                img = R.drawable.storm
            }
        }
        return img
    }
}