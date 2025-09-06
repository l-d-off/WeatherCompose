package ru.darf.weathercompose.domain.usecase

import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(city: City) {
        return weatherRepository.deleteCity(
            city = city
        )
    }
}
