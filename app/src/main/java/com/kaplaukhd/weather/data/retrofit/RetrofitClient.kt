package com.kaplaukhd.weather.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val logging = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        }
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()


    fun getClient(url: String): Retrofit {
        val proxy  = Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("5.161.86.206",1080))
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .proxy(proxy)
//            .build()
//
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


}