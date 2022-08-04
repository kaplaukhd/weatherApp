package com.kaplaukhd.weather.ui.viewmodels


import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.lifecycle.*
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.data.repository.WeatherRepository
import com.kaplaukhd.weather.model.Result
import com.kaplaukhd.weather.model.WeatherApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    var location: List<String> = listOf("12.220","36.55")
    private var _weather = MutableLiveData<Result<WeatherApiResponse>>()
    val weather: LiveData<Result<WeatherApiResponse>>
        get() = _weather

    init {
        getWeather()
    }


   @SuppressLint("MissingPermission")
   private fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _weather.postValue(repository.getWeather(location[0], location[1]))
        }
    }
}

