package ru.darf.weathercompose.core.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

abstract class BaseViewModel : ViewModel() {
    fun popBackStack(navController: NavHostController) {
        val lifecycleState = navController.currentBackStackEntry?.lifecycle?.currentState
        if (lifecycleState == Lifecycle.State.RESUMED) {
            navController.popBackStack()
        }
    }
}