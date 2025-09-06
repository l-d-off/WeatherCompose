package ru.darf.weathercompose.ui.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.data.local.DataStorePrefs
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.data.model.UserData
import ru.darf.weathercompose.ui.main.APP_GRAPH
import ru.darf.weathercompose.ui.screen.weather.WeatherScreen
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val prefs: DataStorePrefs,
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

            prefs.updateUserData {
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