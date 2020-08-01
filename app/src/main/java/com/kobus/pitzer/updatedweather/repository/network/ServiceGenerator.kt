package com.kobus.pitzer.updatedweather.repository.network

import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


class ServiceGenerator(
    baseUrl: String
) {
    private val loggingInterceptor = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.v(message)
            }
        })


    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .callTimeout(40, TimeUnit.SECONDS)
        .build()

    var gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, HtmlAdapter())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(client)
        .build()

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun <T> createService(c: Class<T>): T {
        return retrofit.create(c)
    }
}
