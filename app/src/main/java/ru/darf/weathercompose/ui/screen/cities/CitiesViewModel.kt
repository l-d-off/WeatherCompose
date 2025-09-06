package ru.darf.weathercompose.ui.screen.cities

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.data.local.DataStorePrefs
import ru.darf.weathercompose.domain.model.City
import ru.darf.weathercompose.domain.usecase.DeleteCityUseCase
import ru.darf.weathercompose.domain.usecase.GetLocalCitiesUseCase
import ru.darf.weathercompose.domain.usecase.InsertCityUseCase
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getLocalCitiesUseCase: GetLocalCitiesUseCase,
    private val insertCityUseCase: InsertCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    @param:ApplicationContext private val context: Context,
    private val prefs: DataStorePrefs,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CitiesViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            startLoading()
            val cities = getLocalCitiesUseCase()
            _viewState.update {
                it.copy(
                    cities = cities
                )
            }
            stopLoading()
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
                    cities = cities
                )
            }
            stopLoading()
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            startLoading()
            deleteCityUseCase(city)
            val cities = getLocalCitiesUseCase()
            _viewState.update {
                it.copy(
                    cities = cities
                )
            }
            stopLoading()
        }
    }
}