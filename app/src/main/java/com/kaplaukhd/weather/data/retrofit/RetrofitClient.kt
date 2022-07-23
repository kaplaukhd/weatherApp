package com.kaplaukhd.weather.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun getClient(): RetrofitServices {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(RetrofitServices::class.java)
    }


}