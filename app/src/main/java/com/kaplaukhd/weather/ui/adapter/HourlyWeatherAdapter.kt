package com.kaplaukhd.weather.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.utils.WeatherImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HourlyWeatherAdapter(private val dataset: WeatherApiResponse) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_wether_item, parent, false))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.temp.text = dataset.hourly[position].temp.roundToInt().toString().plus("°")
        val date = dataset.hourly[position].dt
        holder.date.text = SimpleDateFormat("HH:mm").format(Date(date.toLong() * 1000))
        holder.img.setImageResource(WeatherImage.getImage(dataset.hourly[position].weather[0].description))
    }


    override fun getItemCount() = 24

    class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temp: TextView = view.findViewById(R.id.hourly_temp_txt)
        val date: TextView = view.findViewById(R.id.hourly_date_txt)
        val img: ImageView = view.findViewById(R.id.hourly_weather_img)
    }
}