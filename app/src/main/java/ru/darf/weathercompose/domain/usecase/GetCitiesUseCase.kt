package ru.darf.weathercompose.domain.usecase

import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(name: String): NetworkState<List<City>> {
        return weatherRepository.getCities(
            name = name
        )
    }
}
