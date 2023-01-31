package com.example.composecleanarchitectureweatherapp.data.repositoryimpl

import com.example.composecleanarchitectureweatherapp.data.api.WeatherApi
import com.example.composecleanarchitectureweatherapp.domain.BaseResponse
import com.example.composecleanarchitectureweatherapp.domain.model.WeatherApiResponse
import com.example.composecleanarchitectureweatherapp.domain.repository.WeatherRepository
import com.example.composecleanarchitectureweatherapp.helpers.NetworkUtils
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {
    override suspend fun getWeatherData(city: String): BaseResponse<WeatherApiResponse> {
        return try {
            NetworkUtils.getFormatedResponse<WeatherApiResponse>(weatherApi.getWeatherData(city))
        } catch (ex: Exception) {
            ex.printStackTrace()
            BaseResponse.Error(ex.message.toString())
        }
    }

}