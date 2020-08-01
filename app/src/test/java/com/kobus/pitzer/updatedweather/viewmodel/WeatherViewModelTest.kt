package com.kobus.pitzer.updatedweather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kobus.pitzer.updatedweather.repository.WeatherRepository
import com.kobus.pitzer.updatedweather.repository.constant.WeatherTypes
import com.kobus.pitzer.updatedweather.repository.viewmodels.WeatherViewModel
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class WeatherViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repo = Mockito.mock(WeatherRepository::class.java)

    private var weatherViewModel: WeatherViewModel

    init {
        weatherViewModel = WeatherViewModel(repo)
    }

    @Test
    fun testTypeThunderstorm() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(200)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(232)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
    }

    @Test
    fun testTypeDrizzle() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(301)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(321)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
    }

    @Test
    fun testTypeRain() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(500)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(531)
        assertThat(type?.equals(WeatherTypes.RAINY), CoreMatchers.`is`(true))
    }

    @Test
    fun testTypeSunny() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(800)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(201)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(false))
        type = weatherViewModel.getWeatherTypeBasedOnID(801)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(false))
    }

    @Test
    fun testTypeCloudy() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(801)
        assertThat(type?.equals(WeatherTypes.CLOUDY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(804)
        assertThat(type?.equals(WeatherTypes.CLOUDY), CoreMatchers.`is`(true))
    }

    @Test
    fun testTypeOtherScenarios() {
        var type = weatherViewModel.getWeatherTypeBasedOnID(701)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(781)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(true))
        type = weatherViewModel.getWeatherTypeBasedOnID(900)
        assertThat(type?.equals(WeatherTypes.SUNNY), CoreMatchers.`is`(true))
    }

}