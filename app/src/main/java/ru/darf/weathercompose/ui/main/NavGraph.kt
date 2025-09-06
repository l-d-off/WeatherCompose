package ru.darf.weathercompose.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.darf.weathercompose.ui.screen.auth.AuthScreen
import ru.darf.weathercompose.ui.screen.cities.CitiesScreen
import ru.darf.weathercompose.ui.screen.splash.SplashScreen
import ru.darf.weathercompose.ui.screen.weather.WeatherScreen

const val APP_GRAPH = "APP_GRAPH"

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .imePadding(),
        containerColor = Color.Unspecified,
        contentColor = Color.Unspecified
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = SplashScreen.route,
            route = APP_GRAPH
        ) {
            SplashScreen(this, navController).addScreen()
            AuthScreen(this, navController).addScreen()
            WeatherScreen(this, navController).addScreen()
            CitiesScreen(this, navController).addScreen()
        }
    }
}
