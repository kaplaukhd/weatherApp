package com.kaplaukhd.weather.data.retrofit

import com.kaplaukhd.weather.BuildConfig
import com.kaplaukhd.weather.data.retrofit.Utils.API_KEY
import com.kaplaukhd.weather.data.retrofit.Utils.LANG
import com.kaplaukhd.weather.data.retrofit.Utils.UNIT
import com.kaplaukhd.weather.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("/data/2.5/onecall")
   suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") metric: String = UNIT,
        @Query("appid") appId: String = API_KEY,
        @Query("lang") lang: String = LANG,
    ): Response<WeatherApiResponse>
}

object Utils{
    const val UNIT = "metric"
    const val LANG = "ru"
    const val API_KEY = BuildConfig.APIKEY
}

