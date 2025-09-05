package ru.darf.weathercompose.ui.screen.cities

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.logger.logE
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.domain.model.NetworkState
import ru.darf.weathercompose.domain.usecase.GetWeathersUseCase
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getWeathersUseCase: GetWeathersUseCase,
    @param:ApplicationContext private val context: Context,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CitiesViewState())
    val viewState = _viewState.asStateFlow()

    init {
//        viewModelScope.launch {
//            startLoading()
//            val state = viewState.value
//            val city = state.cities.first()
//            val response = getWeathersUseCase(
//                latitude = city.latitude,
//                longitude = city.longitude
//            )
//            when (response) {
//                is NetworkState.Success -> {
//                    val weather = response.data?.weatherToday ?: run {
//                        Toast.makeText(
//                            context,
//                            R.string.app_alert_request_error,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        stopLoading()
//                        return@launch
//                    }
//                    _viewState.update {
//                        it.copy(weathers = listOf(weather))
//                    }
//                }
//
//                is NetworkState.Error -> {
//                    logE(response.error.message)
//                    Toast.makeText(
//                        context,
//                        R.string.app_alert_request_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                is NetworkState.ServerError -> {
//                    Toast.makeText(
//                        context,
//                        R.string.app_alert_server_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//            stopLoading()
//        }
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
}