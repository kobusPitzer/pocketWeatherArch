package com.kobus.pitzer.updatedweather.bindingAdapters

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.updatedweather.R
import com.kobus.pitzer.updatedweather.repository.constant.WeatherTypes
import timber.log.Timber

//ImageView src
@BindingAdapter(value = ["imageIcon"])
fun setImageSource(imageView: ImageView, weatherTypes: WeatherTypes?) {
    Timber.i("setImageSource %s", weatherTypes)

    when (weatherTypes) {
        WeatherTypes.SUNNY -> imageView.setImageResource(R.drawable.sea_sunny)
        WeatherTypes.RAINY -> imageView.setImageResource(R.drawable.sea_rainy)
        WeatherTypes.CLOUDY -> imageView.setImageResource(R.drawable.sea_cloudy)
        else -> imageView.setImageResource(R.drawable.sea_sunny)
    }
}

//RecyclerViewBackground src
@BindingAdapter(value = ["backgroundColour"])
fun setRecyclerViewBackground(recyclerView: RecyclerView, weatherTypes: WeatherTypes?) {
    Timber.i("setImageSource %s", weatherTypes)
    when (weatherTypes) {
        WeatherTypes.SUNNY -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.sea
            )
        )
        WeatherTypes.RAINY -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.rainy
            )
        )
        WeatherTypes.CLOUDY -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.cloudy
            )
        )
        else -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.sea
            )
        )
    }
}