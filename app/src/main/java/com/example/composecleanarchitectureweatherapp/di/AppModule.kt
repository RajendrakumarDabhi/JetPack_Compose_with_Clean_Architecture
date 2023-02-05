package com.example.composecleanarchitectureweatherapp.di

import android.app.Application
import com.example.composecleanarchitectureweatherapp.data.api.WeatherApi
import com.example.composecleanarchitectureweatherapp.domain.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("X-Api-Key",Constants.API_KEY)
                    .build()
                    .let(chain::proceed)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.WeatherApiURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

}