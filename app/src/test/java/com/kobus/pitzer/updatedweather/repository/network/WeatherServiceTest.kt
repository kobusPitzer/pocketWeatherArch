package com.kobus.pitzer.updatedweather.repository.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kobus.pitzer.updatedweather.repository.models.WeatherOneCall
import com.kobus.pitzer.updatedweather.utils.LiveDataTestUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class WeatherServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: WeatherService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WeatherService::class.java)
    }


    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getWeather() {
        val lat = "-25.67"
        val long = "28.25"
        enqueueResponse("weather.json")
        val weatherData =
            LiveDataTestUtil.getValue(
                service.getLocationWeatherDaily(
                    lat,
                    long
                )
            ).body

        val request = mockWebServer.takeRequest()

        assertThat(
            request.path,
            `is`("/onecall?exclude=hourly,minutely&appid=b68056a2a9e54ee3d921023573a87dda&units=metric&lat=-25.67&lon=28.25")
        )

        assertThat<WeatherOneCall>(weatherData, notNullValue())
        assertThat(weatherData?.current?.dt, `is`(1596381312L))
        assertThat(weatherData?.current?.weather?.first()?.id, `is`(800))
        assertThat(weatherData?.daily?.get(3)?.dt, `is`(1596621600L))
        assertThat(weatherData?.daily?.get(4)?.temp?.max, `is`(20.6))
        assertThat(weatherData?.daily?.get(5)?.weather?.first()?.id, `is`(800))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName")
        if (inputStream == null)
            assert(true)
        inputStream?.source()?.buffer()
        val source = inputStream?.source()?.buffer()
        if (source == null)
            assert(true)
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source!!.readString(Charsets.UTF_8))
        )
    }

}