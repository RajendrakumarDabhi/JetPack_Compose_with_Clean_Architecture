package com.example.composecleanarchitectureweatherapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecleanarchitectureweatherapp.domain.BaseResponse
import com.example.composecleanarchitectureweatherapp.domain.location.LocationTracker
import com.example.composecleanarchitectureweatherapp.domain.repository.WeatherRepository
import com.example.composecleanarchitectureweatherapp.presentation.views.mainscreen.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var mutableScreenState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    private val screenState = mutableScreenState.asStateFlow()

    fun getScreenState(): StateFlow<WeatherState> {
        return screenState
    }

    fun loadWeatherData() {
        mutableScreenState.value = WeatherState.Loading
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            weatherRepository.getWeatherData(
                location?.latitude.toString(),
                location?.longitude.toString()
            )?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        mutableScreenState.value =
                            it.data?.let { it1 -> WeatherState.Success(it1) }!!
                    }
                    is BaseResponse.Error -> {
                        mutableScreenState.value = WeatherState.Error("Error:${it.message}")
                    }
                }
            }
        }
    }
}