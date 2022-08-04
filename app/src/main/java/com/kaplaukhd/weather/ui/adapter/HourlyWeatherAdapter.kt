package com.kaplaukhd.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.model.WeatherApiResponse
import kotlin.math.roundToInt

class HourlyWeatherAdapter(private val dataset: ArrayList<WeatherApiResponse>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_wether_item, parent, false))
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = dataset[position]
        holder.temp.text = item.hourly[position].temp.roundToInt().toString().plus("°")
        holder.date.text = item.hourly[position].dt.toString()
        when (item.hourly[position].weather[0].main) {
            "дождь" -> {
                holder.img.setImageResource(R.drawable.heavy_rain)
            }
            "переменная облачность" -> {
                holder.img.setImageResource(R.drawable.partly_cloudy)
            }
            "пасмурно" -> {
                holder.img.setImageResource(R.drawable.cloudy)
            }
            "небольшой дождь" -> {
                holder.img.setImageResource(R.drawable.rainy)
            }
            "облачно с прояснениями" -> {
                holder.img.setImageResource(R.drawable.clouds)
            }
            "Clear" -> {
                holder.img.setImageResource(R.drawable.sun)
            }
            "гроза" -> {
                holder.img.setImageResource(R.drawable.storm)
            }
        }
    }


    override fun getItemCount() = dataset.size

    class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temp: TextView = view.findViewById(R.id.hourly_temp_txt)
        val date: TextView = view.findViewById(R.id.hourly_date_txt)
        val img: ImageView = view.findViewById(R.id.hourly_weather_img)
    }
}