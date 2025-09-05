package ru.darf.weathercompose.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.darf.weathercompose.ui.screen.splash.SplashScreen

const val APP_GRAPH = "APP_GRAPH"

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        containerColor = Color.White,
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
//            AuthScreen(this, navController).addScreen()
//            WeatherScreen(this, navController).addScreen()
//            CitiesScreen(this, navController).addScreen()
        }

        val snackBarHost = remember { SnackbarHostState() }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp),
                hostState = snackBarHost,
            ) {
                Snackbar {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = it.visuals.message,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
