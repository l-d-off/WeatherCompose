package ru.darf.weathercompose.ui.screen.cities

import ru.darf.weathercompose.domain.model.City

data class CitiesViewState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val openSearchCityDialog: Boolean = false,
)
