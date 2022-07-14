package com.kaplaukhd.weather.ui.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.data.WeatherRepository
import com.kaplaukhd.weather.data.retrofit.RetrofitClient
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.utils.RequestPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainViewModel(application: Application) :
    AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val retrofit = RetrofitClient.getClient()
    private val repository = WeatherRepository(retrofit)
    val weather = MutableLiveData<WeatherApiResponse>()
    val city = MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = MutableLiveData<String>().also {
        it.postValue(currentDate())
    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDate(): String {
        val date = Date()
        val current = LocalDateTime.now().month
        val dateFrm = SimpleDateFormat("d", Locale.getDefault())
        return dateFrm.format(date).plus(" $current")
    }

    fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("d/MM", Locale.getDefault())
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun getTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("kk:mm", Locale.getDefault())
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }


    fun geo() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            RequestPermission().requestPermission(context, Activity())
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val i = location?.latitude
                val j = location?.longitude
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(i!!, j!!, 1)[0].getAddressLine(0).apply {
                    city.postValue(this.replaceAfter(Int.toString(), ""))
                }
//                city.postValue(geocoder.getFromLocation(i!!, j!!, 1)[0].locality)
                    CoroutineScope(Dispatchers.IO).launch {
                        weather.postValue(repository.getWeather(i.toString(), j.toString()))
                    }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Не удалось получить геопозицию", Toast.LENGTH_LONG).show()
            }
    }

}