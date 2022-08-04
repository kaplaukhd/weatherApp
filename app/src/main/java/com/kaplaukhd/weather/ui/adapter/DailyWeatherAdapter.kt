package com.kaplaukhd.weather.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.model.WeatherApiResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyWeatherAdapter( private val dataset: ArrayList<WeatherApiResponse>) :
    RecyclerView.Adapter<DailyWeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = dataset[position]
        val date = SimpleDateFormat("d/MM", Locale.getDefault()).format(Date())
        holder.dailyDate.text = date.plus(" ${item.daily[position].weather[0].description}")
        holder.dailyTemp.text =
            item.daily[position].temp.max.roundToInt().toString().plus("°/${item.daily[position].temp.min.roundToInt()}°")
        when (item.daily[position].weather[0].main) {
            "дождь" -> {
                holder.dailyImg.setImageResource(R.drawable.heavy_rain)
            }
            "переменная облачность" -> {
                holder.dailyImg.setImageResource(R.drawable.partly_cloudy)
            }
            "пасмурно" -> {
                holder.dailyImg.setImageResource(R.drawable.cloudy)
            }
            "небольшой дождь" -> {
                holder.dailyImg.setImageResource(R.drawable.rainy)
            }
            "облачно с прояснениями" -> {
                holder.dailyImg.setImageResource(R.drawable.clouds)
            }
            "Clear" -> {
                holder.dailyImg.setImageResource(R.drawable.sun)
            }
            "гроза" -> {
                holder.dailyImg.setImageResource(R.drawable.storm)
            }
        }
    }

    override fun getItemCount() = dataset.size

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val dailyTemp: TextView = view.findViewById(R.id.daily_tem)
        val dailyImg: ImageView = view.findViewById(R.id.daily_img)
        val dailyDate: TextView = view.findViewById(R.id.daily_date)
        override fun onClick(p0: View?) {
           Toast.makeText(p0?.context, "Hello", Toast.LENGTH_LONG ).show()
        }
    }
}