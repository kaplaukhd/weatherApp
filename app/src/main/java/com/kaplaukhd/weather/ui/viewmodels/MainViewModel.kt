package com.kaplaukhd.weather.ui.viewmodels


import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.data.repository.WeatherRepository
import com.kaplaukhd.weather.model.WeatherApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    val weather = MutableLiveData<WeatherApiResponse>()

    val currentDate = MutableLiveData<String>().also {
        it.postValue(currentDate())
    }


    private fun currentDate(): String {
        val date = Date()
        val dateFrm = SimpleDateFormat("d LLLL", Locale.getDefault())
        return dateFrm.format(date)
    }

    fun getDateTime(s: String): String? {
        val sdf = SimpleDateFormat("d/MM", Locale.getDefault())
        val sdf2 = SimpleDateFormat("kk:mm", Locale.getDefault())
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    }


     fun getWeather(latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            weather.postValue(repository.getWeather(latitude.toString(), longitude.toString()))
        }
    }
}