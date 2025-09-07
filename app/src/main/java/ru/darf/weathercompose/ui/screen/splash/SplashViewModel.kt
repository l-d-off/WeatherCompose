package ru.darf.weathercompose.ui.screen.splash

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.darf.weathercompose.core.viewmodel.BaseViewModel
import ru.darf.weathercompose.domain.usecase.userdata.GetUserDataUseCase
import ru.darf.weathercompose.ui.screen.auth.AuthScreen
import ru.darf.weathercompose.ui.screen.weather.WeatherScreen
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
) : BaseViewModel() {

    fun goToNextScreen(navController: NavHostController) {
        viewModelScope.launch {
            delay(1000)
            val userData = getUserDataUseCase().firstOrNull()
            val hasUserData = userData?.login != null
            when (hasUserData) {
                true -> WeatherScreen.navigate(navController) {
                    popUpTo(SplashScreen.route) {
                        inclusive = true
                    }
                }

                false -> AuthScreen.navigate(navController) {
                    popUpTo(SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}