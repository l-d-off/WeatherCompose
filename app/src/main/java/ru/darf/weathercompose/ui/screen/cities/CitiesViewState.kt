package ru.darf.weathercompose.ui.screen.cities

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.darf.weathercompose.domain.model.City

@Immutable
data class CitiesViewState(
    val localCities: ImmutableList<City> = persistentListOf(),
    val searchCities: ImmutableList<City> = persistentListOf(),
    val searchText: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val openSearchCityDialog: Boolean = false,
)
