package ru.darf.weathercompose.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.darf.weathercompose.R
import ru.darf.weathercompose.utils.compose.LifecycleEvent
import ru.darf.weathercompose.utils.router.ScreenCompanionRouter

class SplashScreen(
    private val navBuilder: NavGraphBuilder,
    private val navController: NavHostController,
) {
    companion object : ScreenCompanionRouter()

    fun addScreen() = navBuilder.composable(route) { entry ->

        val model = hiltViewModel<SplashViewModel>(entry)

        LifecycleEvent(
            onStart = {
                model.goNextScreen(navController)
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
            modifier = Modifier
//                        .padding(end = DimApp.mediumMargin)
//                        .size(DimApp.logInIconSize)
                .clip(shape = RoundedCornerShape(percent = 8)),
            painter = painterResource(R.drawable.ic_app),
            contentDescription = "icon_krasnodar"
        )
//                Text(
//                    style = ThemeApp.typography.headlineLarge,
//                    text = StringApp.title,
//                    color = ThemeApp.colors.primary
//                )
    }
}