package ru.darf.weathercompose.domain.usecase

import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(cityId: Int) {
        return weatherRepository.deleteCityById(
            cityId = cityId
        )
    }
}
