package com.kaplaukhd.weather

import android.app.Application
import com.kaplaukhd.weather.data.AppComponent
import com.kaplaukhd.weather.data.DaggerAppComponent

class App: Application() {
lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}