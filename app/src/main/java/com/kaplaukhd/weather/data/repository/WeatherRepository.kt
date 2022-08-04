package com.kaplaukhd.weather.data.repository

import com.kaplaukhd.weather.data.retrofit.RetrofitServices
import com.kaplaukhd.weather.model.Result
import com.kaplaukhd.weather.model.WeatherApiResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val retrofitServices: RetrofitServices) :
    BaseRepository() {
    suspend fun getWeather(i: String, j: String): Result<WeatherApiResponse> = apiCall {
        retrofitServices.getWeather(i, j)
    }
}
