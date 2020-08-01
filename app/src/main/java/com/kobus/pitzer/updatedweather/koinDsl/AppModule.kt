package com.kobus.pitzer.updatedweather.koinDsl

import androidx.room.Room
import com.kobus.pitzer.updatedweather.repository.WeatherRepository
import com.kobus.pitzer.updatedweather.repository.WeatherRepositoryImpl
import com.kobus.pitzer.updatedweather.repository.constant.DbFile
import com.kobus.pitzer.updatedweather.repository.core.AppExecutors
import com.kobus.pitzer.updatedweather.repository.db.AppDatabase
import com.kobus.pitzer.updatedweather.repository.db.AppDatabaseCallback
import com.kobus.pitzer.updatedweather.repository.db.DBHelper
import com.kobus.pitzer.updatedweather.repository.network.ServiceGenerator
import com.kobus.pitzer.updatedweather.repository.network.WeatherService
import com.kobus.pitzer.updatedweather.repository.preferences.CorePreferences
import com.kobus.pitzer.updatedweather.repository.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {
    single { AppExecutors.createInstance() }

    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get()) }

    // Network Services  - Auth required
    single {
        ServiceGenerator(
            CorePreferences.getBaseUrl()
        )
    }

    single { (get() as ServiceGenerator).createService(WeatherService::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DbFile.APP_DB_FILE.fileName
        )
            .openHelperFactory(DBHelper.getPassPhrase())
            .fallbackToDestructiveMigration()
            .addCallback(AppDatabaseCallback(get()))
            .build()
    }

    viewModel { WeatherViewModel(get()) }
}