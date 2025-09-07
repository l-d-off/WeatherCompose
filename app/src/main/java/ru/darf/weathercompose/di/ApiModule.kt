package ru.darf.weathercompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.darf.weathercompose.data.remote.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit)
            : WeatherApi = retrofit.create(WeatherApi::class.java)
}