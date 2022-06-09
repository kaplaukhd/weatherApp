package com.kaplaukhd.weather.data.retrofit

object Common {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofitServices: RetrofitServices
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}