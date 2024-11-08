package com.peramal.weatherapplication.di

import com.peramal.weatherapplication.common.ApiConfig.BASE_URL
import com.peramal.weatherapplication.network.WeatherApi
import com.peramal.weatherapplication.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepository(api)
    }
}