package com.kaplaukhd.weather.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaplaukhd.weather.R
import com.kaplaukhd.weather.model.WeatherApiResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter(context: Context, private val dataset: ArrayList<WeatherApiResponse>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = dataset[position]
        val sdf = SimpleDateFormat("d LLLL", Locale.getDefault())
        val formatDate = sdf.format(Date())
        holder.dailyDate.text = formatDate.plus(" ${item.daily[position].weather[0].description}")
        holder.dailyTemp.text =
            item.daily[position].temp.max.toString().plus("/${item.daily[position].temp.max}")
                .toString()
    }

    override fun getItemCount() = dataset.size

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dailyTemp = view.findViewById<TextView>(R.id.daily_tem)
        val dailyImg = view.findViewById<ImageView>(R.id.daily_img)
        val dailyDate = view.findViewById<TextView>(R.id.daily_date)
    }
}