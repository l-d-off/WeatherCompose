package ru.darf.weathercompose.ui.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.domain.model.UserData
import ru.darf.weathercompose.domain.usecase.userdata.UpdateUserDataUseCase
import ru.darf.weathercompose.ui.screen.weather.WeatherScreen
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(AuthViewState())
    val viewState = _viewState.asStateFlow()

    fun goToWeatherScreen(context: Context, navController: NavHostController) {
        viewModelScope.launch {

            val state = viewState.value
            val loginString = state.login.text
            val passwordString = state.password.text

            if (loginString.isBlank()) {
                Toast.makeText(
                    context,
                    R.string.auth_screen_alert_login_not_blank,
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            if (passwordString.isBlank()) {
                Toast.makeText(
                    context,
                    R.string.auth_screen_alert_password_not_blank,
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            updateUserDataUseCase {
                UserData(
                    login = loginString,
                    password = passwordString
                )
            }

            WeatherScreen.navigate(navController) {
                popUpTo(AuthScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    fun updateLogin(value: TextFieldValue) {
        _viewState.update {
            it.copy(
                login = value
            )
        }
    }

    fun updatePassword(value: TextFieldValue) {
        _viewState.update {
            it.copy(
                password = value
            )
        }
    }
}