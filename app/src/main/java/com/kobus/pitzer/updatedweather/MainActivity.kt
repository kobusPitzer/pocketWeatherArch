package com.kobus.pitzer.updatedweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kobus.pitzer.updatedweather.databinding.ActivityMainBinding
import com.kobus.pitzer.updatedweather.fragments.WeatherViewFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var weatherViewFragment = WeatherViewFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setInitialFragment()
    }

    private fun setInitialFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, weatherViewFragment)
        ft.commit()
    }
}