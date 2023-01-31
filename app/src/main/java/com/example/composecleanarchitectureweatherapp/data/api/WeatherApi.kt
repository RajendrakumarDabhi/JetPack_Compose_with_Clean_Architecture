package com.example.composecleanarchitectureweatherapp.data.api

import com.example.composecleanarchitectureweatherapp.domain.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherData(@Query("city") city: String):Response<WeatherApiResponse>

}