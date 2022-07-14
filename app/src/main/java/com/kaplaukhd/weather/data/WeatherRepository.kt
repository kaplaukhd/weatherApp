package com.kaplaukhd.weather.data

import com.kaplaukhd.weather.data.retrofit.RetrofitServices
import com.kaplaukhd.weather.model.WeatherApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class WeatherRepository(private val retrofitServices: RetrofitServices) {
    suspend fun getWeather(i: String, j: String): WeatherApiResponse{
        return withContext(Dispatchers.IO){
            retrofitServices.getHourlyWeather(i, j)
        }.await()
    }
}