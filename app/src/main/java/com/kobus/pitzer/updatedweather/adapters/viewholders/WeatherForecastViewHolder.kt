package com.kobus.pitzer.updatedweather.adapters.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.updatedweather.R
import com.kobus.pitzer.updatedweather.repository.models.Daily
import java.util.*
import kotlin.math.floor

class WeatherForecastViewHolder(val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvDay: TextView = itemView.findViewById(R.id.day)
    private val tvMaxTemp: TextView = itemView.findViewById(R.id.maxTemp)
    private val ivWeatherType: ImageView = itemView.findViewById(R.id.weatherType)

    private val degreeText = "\u00B0"

    fun onBindViewHolder(daily: Daily, position: Int) {
        val c = Calendar.getInstance()
        c.timeInMillis = daily.dt?.times(1000) ?: c.timeInMillis
        tvDay.text = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        ivWeatherType.setImageResource(getImageBasedOnType(daily.weather?.first()?.id))
        tvMaxTemp.text = daily.temp?.max?.let { floor(it).toInt().toString() + degreeText }
    }

    private fun getImageBasedOnType(id: Int?): Int {
        when (id) {
            in 200..622 -> return R.mipmap.rain
            800 -> return R.mipmap.clear
            in 801..805 -> return R.mipmap.partlysunny
            else -> return R.mipmap.clear
        }
    }
}