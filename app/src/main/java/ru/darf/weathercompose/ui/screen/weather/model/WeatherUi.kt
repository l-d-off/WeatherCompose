package ru.darf.weathercompose.ui.screen.weather.model

import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.model.WeatherCondition

data class WeatherUi(
    val city: City,
    val temperature: Double,
    val weatherCondition: WeatherCondition,
)
