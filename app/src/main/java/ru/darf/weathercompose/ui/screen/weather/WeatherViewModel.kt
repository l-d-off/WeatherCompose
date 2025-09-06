package ru.darf.weathercompose.ui.screen.weather

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.data.local.DataStorePrefs
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.usecase.GetLocalCitiesUseCase
import ru.darf.weathercompose.domain.usecase.GetWeathersUseCase
import ru.darf.weathercompose.ui.screen.auth.AuthScreen
import ru.darf.weathercompose.ui.screen.cities.CitiesScreen
import ru.darf.weathercompose.ui.screen.weather.model.WeatherUi
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeathersUseCase: GetWeathersUseCase,
    private val getLocalCitiesUseCase: GetLocalCitiesUseCase,
    @param:ApplicationContext private val context: Context,
    private val prefs: DataStorePrefs,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(WeatherViewState())
    val viewState = _viewState.asStateFlow()

    init {
        val firstJob = viewModelScope.launch {
            startLoading()
            val cities = getLocalCitiesUseCase()
            val response = getWeathersUseCase(
                latitudeString = cities.joinToString(",") { it.latitude.toString() },
                longitudeString = cities.joinToString(",") { it.longitude.toString() }
            )
            when (response) {
                is NetworkState.Success -> {
                    val weathers = response.data ?: run {
                        Toast.makeText(
                            context,
                            R.string.app_alert_request_error,
                            Toast.LENGTH_SHORT
                        ).show()
                        stopLoading()
                        return@launch
                    }
                    _viewState.update {
                        it.copy(
                            weathers = weathers.zip(cities) { weather, city ->
                                WeatherUi(
                                    city = city,
                                    temperature = weather.temperature,
                                    weatherCondition = weather.getWeatherCondition()
                                )
                            }
                        )
                    }
                }

                is NetworkState.Error -> {
                    Toast.makeText(
                        context,
                        R.string.app_alert_request_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkState.ServerError -> {
                    Toast.makeText(
                        context,
                        R.string.app_alert_server_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            stopLoading()
        }
        viewModelScope.launch {
            firstJob.join()
            while (true) {
                delay(10000L)
                refreshData()
            }
        }
    }

    fun goToCitiesScreen(navController: NavHostController) {
        CitiesScreen.navigate(navController)
    }

    fun signOut(navController: NavHostController) {
        viewModelScope.launch {
            prefs.deleteUserData()
            navController.navigate(AuthScreen.route) {
                popUpTo(WeatherScreen.route) {
                    inclusive = true
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

    fun refreshTrigger() {
        _viewState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            refreshData()
            _viewState.update { it.copy(isRefreshing = false) }
        }
    }

    private suspend fun refreshData() {
        val cities = getLocalCitiesUseCase()
        val response = getWeathersUseCase(
            latitudeString = cities.joinToString(",") { it.latitude.toString() },
            longitudeString = cities.joinToString(",") { it.longitude.toString() }
        )
        when (response) {
            is NetworkState.Success -> {
                val weathers = response.data ?: return
                _viewState.update {
                    it.copy(
                        weathers = weathers.zip(cities) { weather, city ->
                            WeatherUi(
                                city = city,
                                temperature = weather.temperature,
                                weatherCondition = weather.getWeatherCondition()
                            )
                        }
                    )
                }
            }

            is NetworkState.Error -> {}

            is NetworkState.ServerError -> {}
        }
    }
}