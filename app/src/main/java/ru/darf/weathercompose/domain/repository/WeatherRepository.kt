package ru.darf.weathercompose.domain.repository

import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeathers(
        latitudeString: String,
        longitudeString: String,
    ): NetworkState<List<Weather>>

    suspend fun getCities(
        name: String,
    ): NetworkState<List<City>>

    suspend fun getLocalCities(): List<City>

    suspend fun insertCity(city: City)

    suspend fun deleteCity(city: City)
}
