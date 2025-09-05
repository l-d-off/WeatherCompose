package ru.darf.weathercompose.ui.screen.weather

import ru.darf.weathercompose.ui.screen.weather.model.WeatherUi

data class WeatherViewState(
    val weathers: List<WeatherUi> = emptyList(),
    val isLoading: Boolean = false,
)
