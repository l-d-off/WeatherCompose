package ru.darf.weathercompose.domain.usecase.weather

import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocalCitiesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(): List<City> {
        return weatherRepository.getLocalCities()
    }
}
