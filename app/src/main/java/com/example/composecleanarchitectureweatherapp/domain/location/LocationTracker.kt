package com.example.composecleanarchitectureweatherapp.domain.location

import android.location.Location


interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}
