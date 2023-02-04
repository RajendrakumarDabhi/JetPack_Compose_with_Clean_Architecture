package com.example.composecleanarchitectureweatherapp.di

import com.example.composecleanarchitectureweatherapp.data.LocationImpl.LocationTrackerImpl
import com.example.composecleanarchitectureweatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTracker: LocationTrackerImpl): LocationTracker

}