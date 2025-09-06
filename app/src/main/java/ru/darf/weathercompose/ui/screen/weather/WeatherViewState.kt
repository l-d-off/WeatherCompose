package ru.darf.weathercompose.ui.screen.weather

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.darf.weathercompose.ui.screen.weather.model.WeatherUi

@Immutable
data class WeatherViewState(
    val weathers: ImmutableList<WeatherUi> = persistentListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
