package com.kaplaukhd.weather.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.kaplaukhd.weather.R

class HourlyWeatherViewHolder(view: View): ViewHolder(view) {
    val img: ImageView = view.findViewById(R.id.hourly_weather_img)
    val temp: TextView = view.findViewById(R.id.hourly_temp_txt)
    val date: TextView = view.findViewById(R.id.hourly_date_txt)
}