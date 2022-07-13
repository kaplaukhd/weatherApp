package com.kaplaukhd.weather.ui.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.kaplaukhd.weather.data.retrofit.Common
import com.kaplaukhd.weather.model.WeatherApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MainViewModel(application: Application) :
    AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        geo()
    }

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


    private fun response(i: String, j: String) {
                Common.retrofitServices.getHourlyWeather(i,j).enqueue(object: Callback<WeatherApiResponse>{
                override fun onResponse(
                    call: Call<WeatherApiResponse>,
                    response: Response<WeatherApiResponse>
                ) {
                        weather.postValue(response.body())
                }

                override fun onFailure(call: Call<WeatherApiResponse>, t: Throwable) {

                }

            })

        }



    @SuppressLint("MissingPermission")
    fun geo() {
        Log.d(TAG, "start geo")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.d(TAG, "success")
                val i = location?.latitude
                val j = location?.longitude
                val geocoder = Geocoder(context, Locale.getDefault())
                city.postValue(geocoder.getFromLocation(i!!, j!!, 1)[0].locality)
                    response(i.toString(), j.toString())
            }
            .addOnFailureListener {
                Log.d(TAG, "failure $it")
            }
    }

    companion object {
        const val TAG = "viewModel"
    }
}