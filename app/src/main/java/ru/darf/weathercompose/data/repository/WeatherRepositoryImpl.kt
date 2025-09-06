package ru.darf.weathercompose.data.repository

import ru.darf.weathercompose.data.local.dao.CityDao
import ru.darf.weathercompose.data.mapper.CityMapper
import ru.darf.weathercompose.data.remote.WeatherApi
import ru.darf.weathercompose.domain.model.Cities
import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.model.Weather
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cityDao: CityDao,
    private val cityMapper: CityMapper,
) : WeatherRepository {

    override suspend fun getWeathers(
        latitudeString: String,
        longitudeString: String,
    ): NetworkState<List<Weather>> {
        return runCatching {
            weatherApi.getWeathers(
                latitudeString = latitudeString,
                longitudeString = longitudeString
            )
        }.mapCatching { response ->
            if (response.isSuccessful) {
                NetworkState.Success(
                    data = response.body()
                )
            } else {
                NetworkState.ServerError(
                    descriptionError = response.message()
                )
            }
        }.getOrElse { NetworkState.Error(it) }
    }

    override suspend fun getCities(
        name: String,
    ): NetworkState<Cities> {
        return runCatching {
            weatherApi.getCities(
                name = name,
            )
        }.mapCatching { response ->
            if (response.isSuccessful) {
                NetworkState.Success(
                    data = response.body()
                )
            } else {
                NetworkState.ServerError(
                    descriptionError = response.message()
                )
            }
        }.getOrElse { NetworkState.Error(it) }
    }

    override suspend fun getLocalCities(): List<City> {
        val cities = cityDao.getAll()
        return cityMapper.mapToDomain(cities)
    }

    override suspend fun insertCity(city: City) {
        val cityEntity = cityMapper.mapToEntity(city)
        cityDao.insert(cityEntity)
    }

    override suspend fun deleteCity(city: City) {
        val cityEntity = cityMapper.mapToEntity(city)
        cityDao.delete(cityEntity)
    }
}