package com.kaplaukhd.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaplaukhd.weather.data.repository.WeatherRepository
import javax.inject.Inject


class ViewModelFactory @Inject constructor(private val repository: WeatherRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}