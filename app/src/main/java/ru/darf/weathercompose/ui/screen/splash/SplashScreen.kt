package ru.darf.weathercompose.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.compose.LifecycleEvent
import ru.darf.weathercompose.core.router.ScreenCompanionRouter

class SplashScreen(
    private val navBuilder: NavGraphBuilder,
    private val navController: NavHostController,
) {
    companion object : ScreenCompanionRouter()

    fun addScreen() = navBuilder.composable(route) { entry ->

        val viewModel = hiltViewModel<SplashViewModel>(entry)

        LifecycleEvent(
            onStart = {
                viewModel.goToNextScreen(navController)
            }
        )

        SplashContent()
    }
}

@Composable
private fun SplashContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(160.dp),
            painter = painterResource(R.drawable.ic_app),
            contentDescription = "icon_krasnodar"
        )
        Spacer(Modifier.height(16.dp))
        Text(
            fontSize = 32.sp,
            text = stringResource(R.string.app_name),
            color = Color.Black
        )
    }
}