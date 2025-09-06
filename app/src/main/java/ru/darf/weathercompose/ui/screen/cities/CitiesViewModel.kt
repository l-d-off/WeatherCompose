package ru.darf.weathercompose.ui.screen.cities

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.usecase.DeleteCityUseCase
import ru.darf.weathercompose.domain.usecase.GetCitiesUseCase
import ru.darf.weathercompose.domain.usecase.GetLocalCitiesUseCase
import ru.darf.weathercompose.domain.usecase.InsertCityUseCase
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getLocalCitiesUseCase: GetLocalCitiesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val insertCityUseCase: InsertCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    @param:ApplicationContext private val context: Context,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CitiesViewState())
    val viewState = _viewState.asStateFlow()

    private val searchTextFlow = MutableStateFlow("")

    init {
        getLocalCities()
        searchCities()
    }

    private fun getLocalCities() {
        viewModelScope.launch {
            startLoading()
            val cities = getLocalCitiesUseCase()
            _viewState.update {
                it.copy(
                    localCities = cities.toImmutableList()
                )
            }
            stopLoading()
        }
    }

    private fun searchCities() {
        viewModelScope.launch {
            searchTextFlow
                .drop(1)
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { text ->
                    val response = getCitiesUseCase(text)
                    when (response) {
                        is NetworkState.Success -> {
                            _viewState.update { state ->
                                state.copy(
                                    searchCities = response.data
                                        ?.toImmutableList()
                                        ?: persistentListOf()
                                )
                            }
                        }

                        is NetworkState.Error -> {
                            _viewState.update { state ->
                                state.copy(
                                    searchCities = persistentListOf()
                                )
                            }
                        }

                        is NetworkState.ServerError -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.app_alert_server_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    fun startLoading() {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    fun stopLoading() {
        _viewState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun updateOpenSearchCityDialog(value: Boolean) {
        _viewState.update {
            it.copy(
                openSearchCityDialog = value
            )
        }
    }

    fun insertCity(city: City) {
        viewModelScope.launch {
            startLoading()
            insertCityUseCase(city)
            val cities = getLocalCitiesUseCase()
            _viewState.update {
                it.copy(
                    localCities = cities.toImmutableList()
                )
            }
            stopLoading()
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            startLoading()
            deleteCityUseCase(city.id)
            val cities = getLocalCitiesUseCase()
            _viewState.update {
                it.copy(
                    localCities = cities.toImmutableList()
                )
            }
            stopLoading()
        }
    }

    fun updateSearchText(value: TextFieldValue) {
        _viewState.update {
            it.copy(searchText = value)
        }
        searchTextFlow.value = value.text
    }
}