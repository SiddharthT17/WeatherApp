package com.peramal.weatherapplication.repository

import com.peramal.weatherapplication.network.WeatherApi
import com.peramal.weatherapplication.network.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) {
    suspend fun getWeather(city: String, apiKey: String): Result<WeatherResponse> {
        return try {
            val response = api.getWeather(city, apiKey)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data"))
            } else {
                Result.failure(Exception("Unable to fetch weather information.\nPlease verify the city and try again"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
