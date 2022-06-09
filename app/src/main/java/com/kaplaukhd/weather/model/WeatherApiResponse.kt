package com.kaplaukhd.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
@SerializedName("lat") val lat : Double,
@SerializedName("lon") val lon : Double,
@SerializedName("current") val current : Current,
@SerializedName("daily") val daily : List<Daily>,
@SerializedName("hourly") val hourly : List<Hourly>
)

data class Current (
    @SerializedName("temp") val temp : Double,
    @SerializedName("uvi") val uvi : Double,
    @SerializedName("feels_like") val feels_like : Double,
    @SerializedName("pressure") val pressure : Int,
    @SerializedName("humidity") val humidity : Int,
    @SerializedName("wind_speed") val wind_speed : Int,
    @SerializedName("weather") val weather : List<CurrentWeather>,
)
data class CurrentWeather(
    @SerializedName("id") val id : Int,
    @SerializedName("main") val main : String,
    @SerializedName("description") val description : String,
    @SerializedName("icon") val icon : String
)

data class Daily (
    @SerializedName("dt") val dt : Int,
    @SerializedName("temp") val temp : Temp,
    @SerializedName("weather") val weather : List<Weather>,
)


data class Temp (
    @SerializedName("day") val day : Double,
    @SerializedName("min") val min : Double,
    @SerializedName("max") val max : Double,
    @SerializedName("night") val night : Double,
    @SerializedName("eve") val eve : Double,
    @SerializedName("morn") val morn : Double
)

data class Weather (
    @SerializedName("id") val id : Int,
    @SerializedName("main") val main : String,
    @SerializedName("description") val description : String,
    @SerializedName("icon") val icon : String
)

data class Hourly (
    @SerializedName("dt") val dt : Int,
    @SerializedName("temp") val temp : Double,
    @SerializedName("weather") val weather : List<HourlyWeather>,
)

data class HourlyWeather(
    @SerializedName("id") val id : Int,
    @SerializedName("main") val main : String,
    @SerializedName("description") val description : String,
    @SerializedName("icon") val icon : String)
