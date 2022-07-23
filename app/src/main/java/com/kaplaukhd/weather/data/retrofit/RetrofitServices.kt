package com.kaplaukhd.weather.data.retrofit

import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.utils.Utils.API_KEY
import com.kaplaukhd.weather.utils.Utils.LANG
import com.kaplaukhd.weather.utils.Utils.UNIT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("onecall")
    fun getHourlyWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") metric: String = UNIT,
        @Query("appid") appid: String = API_KEY,
        @Query("lang") lang: String = LANG,
    ): Call<WeatherApiResponse>
}

