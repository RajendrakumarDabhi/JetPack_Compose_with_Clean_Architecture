package com.example.composecleanarchitectureweatherapp.presentation.views.mainscreen

import com.example.composecleanarchitectureweatherapp.data.api.WeatherApi
import com.example.composecleanarchitectureweatherapp.domain.model.WeatherApiResponse

sealed class WeatherState {
    object Loading : WeatherState()
    class Success(val weatherApiResponse: WeatherApiResponse) : WeatherState()
    class Error(val msg: String) : WeatherState()
}