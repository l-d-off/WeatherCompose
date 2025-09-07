package ru.darf.weathercompose.data.mapper

import ru.darf.weathercompose.data.local.entity.CityEntity
import ru.darf.weathercompose.domain.model.City
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun mapToEntity(city: City): CityEntity {
        return CityEntity(
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude,
            region = city.region
        )
    }

    fun mapToDomain(cityEntity: CityEntity): City {
        return City(
            id = cityEntity.id,
            name = cityEntity.name,
            latitude = cityEntity.latitude,
            longitude = cityEntity.longitude,
            region = cityEntity.region
        )
    }

    fun mapToDomain(cityEntities: List<CityEntity>): List<City> {
        return cityEntities.map(::mapToDomain)
    }

    fun mapToEntity(cities: List<City>): List<CityEntity> {
        return cities.map(::mapToEntity)
    }
}