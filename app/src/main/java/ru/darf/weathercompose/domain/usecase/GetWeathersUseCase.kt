package ru.darf.weathercompose.domain.usecase

import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.model.WeatherToday
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeathersUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(
        latitudeString: String,
        longitudeString: String,
    ): NetworkState<List<WeatherToday>> {
        return when (val response = weatherRepository.getWeathers(
            latitudeString = latitudeString,
            longitudeString = longitudeString
        )) {
            is NetworkState.Success -> NetworkState.Success(response.data?.map { it.weatherToday })
            is NetworkState.Error -> response
            is NetworkState.ServerError -> response
        }
    }
}
