package com.kaplaukhd.weather.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.model.WeatherApiResponse
import com.kaplaukhd.weather.utils.WeatherImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyWeatherAdapter(private val dataset: WeatherApiResponse) :
    RecyclerView.Adapter<DailyWeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.dailyDate.text =
            SimpleDateFormat("dd/MM").format(Date(dataset.daily[position].dt.toLong() * 1000))
                .plus(" ${dataset.daily[position].weather[0].description}")
        holder.dailyTemp.text =
            dataset.daily[position].temp.max.roundToInt().toString()
                .plus("°/${dataset.daily[position].temp.min.roundToInt()}°")
        holder.dailyImg.setImageResource(WeatherImage.getImage(dataset.daily[position].weather[0].description))
    }

    override fun getItemCount() = 3

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val dailyTemp: TextView = view.findViewById(R.id.daily_tem)
        val dailyImg: ImageView = view.findViewById(R.id.daily_img)
        val dailyDate: TextView = view.findViewById(R.id.daily_date)
        override fun onClick(p0: View?) {
            Toast.makeText(p0?.context, "Hello", Toast.LENGTH_LONG).show()
        }
    }
}