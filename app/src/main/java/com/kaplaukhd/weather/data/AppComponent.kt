package com.kaplaukhd.weather.data

import com.kaplaukhd.weather.data.repository.WeatherRepository
import com.kaplaukhd.weather.data.retrofit.RetrofitServices
import com.kaplaukhd.weather.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainActivity)
}

@Module(includes = [NetworkModule::class])
class AppModule() {
    @Provides
    fun provideRepository(retrofitServices: RetrofitServices): WeatherRepository {
        return WeatherRepository(retrofitServices)
    }
}

@Module
class NetworkModule() {
    @Provides
    fun provideRetrofit(): RetrofitServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }
}