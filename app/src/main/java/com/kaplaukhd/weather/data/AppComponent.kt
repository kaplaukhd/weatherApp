package com.kaplaukhd.weather.data

import com.kaplaukhd.weather.data.repository.WeatherRepository
import com.kaplaukhd.weather.data.retrofit.RetrofitClient
import com.kaplaukhd.weather.data.retrofit.RetrofitServices
import com.kaplaukhd.weather.ui.MainActivity
import com.kaplaukhd.weather.ui.viewmodels.MainViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
}

@Module
class AppModule() {
    @Provides
    fun provideRepository(retrofitServices: RetrofitServices): WeatherRepository {
        return WeatherRepository(retrofitServices)
    }

    @Provides
    fun provideRetrofitServices(): RetrofitServices {
        return RetrofitClient.getClient()
    }
}