package com.example.composecleanarchitectureweatherapp.domain.repository

import com.example.composecleanarchitectureweatherapp.domain.BaseResponse
import com.example.composecleanarchitectureweatherapp.domain.model.WeatherApiResponse
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeatherData(lat: String,lng:String): BaseResponse<WeatherApiResponse>
}