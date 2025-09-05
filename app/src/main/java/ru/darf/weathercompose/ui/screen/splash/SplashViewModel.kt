package ru.darf.weathercompose.ui.screen.splash

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.darf.weathercompose.data.local.DataStorePrefs
import ru.darf.weathercompose.utils.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: DataStorePrefs,
) : BaseViewModel() {

    fun goNextScreen(navController: NavHostController) {
        viewModelScope.launch {

        }
    }
}