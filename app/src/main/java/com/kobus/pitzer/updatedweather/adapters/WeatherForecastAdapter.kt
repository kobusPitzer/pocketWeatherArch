package com.kobus.pitzer.updatedweather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.updatedweather.R
import com.kobus.pitzer.updatedweather.adapters.viewholders.WeatherForecastViewHolder
import com.kobus.pitzer.updatedweather.repository.models.Daily
import com.kobus.pitzer.updatedweather.repository.models.Weather

class WeatherForecastAdapter(val context: Context) :
    RecyclerView.Adapter<WeatherForecastViewHolder>() {
    private var weatherForecastList: List<Daily> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_weather_forecast, parent, false)
        return WeatherForecastViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.onBindViewHolder(
            weatherForecastList[position],
            position
        )
    }

    override fun getItemCount(): Int {
        return weatherForecastList.size
    }

    fun getWeatherList(): List<Daily> {
        return weatherForecastList
    }

    fun setWeatherList(newWeatherList: List<Daily>) {
        this.weatherForecastList = newWeatherList
        notifyDataSetChanged()
    }
}