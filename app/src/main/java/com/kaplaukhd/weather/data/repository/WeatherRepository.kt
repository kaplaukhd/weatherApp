package com.kaplaukhd.weather.data.repository

import com.kaplaukhd.weather.data.retrofit.RetrofitServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val retrofitServices: RetrofitServices) {
    suspend fun getWeather(i: String, j: String) = withContext(Dispatchers.IO) {
        retrofitServices.getHourlyWeather(i, j)
    }.await()
}
