package ru.darf.weathercompose.ui.screen.cities

import androidx.compose.ui.text.input.TextFieldValue
import ru.darf.weathercompose.domain.model.City

data class CitiesViewState(
    val localCities: List<City> = emptyList(),
    val searchCities: List<City> = emptyList(),
    val searchText: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val openSearchCityDialog: Boolean = false,
)
