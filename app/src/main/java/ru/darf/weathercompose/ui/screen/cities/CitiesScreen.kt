package ru.darf.weathercompose.ui.screen.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.router.ScreenCompanionRouter
import ru.darf.weathercompose.core.ui.CircularProgressBar
import ru.darf.weathercompose.core.ui.TopBar
import ru.darf.weathercompose.domain.model.WeatherCondition

class CitiesScreen(
    private val navBuilder: NavGraphBuilder,
    private val navController: NavHostController,
) {
    companion object : ScreenCompanionRouter()

    fun addScreen() = navBuilder.composable(route) { entry ->

        val viewModel = hiltViewModel<CitiesViewModel>(entry)
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        CitiesContent(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController
        )
    }
}

@Composable
private fun CitiesContent(
    viewModel: CitiesViewModel,
    viewState: CitiesViewState,
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Unspecified,
        contentColor = Color.Unspecified,
        topBar = {
            TopBar(
                title = stringResource(R.string.weather_screen_title),
                actions = {
                    IconButton(
                        onClick = {
//                            viewModel.goToCitiesScreen(
//                                navController = navController
//                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "Cities"
                            )
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = viewState.cities, // + city
                key = {} // city.id
            ) { weather ->
                Card(
                    content = {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Город",
                                fontSize = 20.sp
                            )
//                            Text(
//                                text = stringResource(
//                                    R.string.weather_screen_temperature_with_param,
//                                    weather.temperature
//                                ),
//                                fontSize = 16.sp,
//                            )
//                            Icon(
//                                painter = painterResource(
//                                    when (weather.getWeatherCondition()) {
//                                        WeatherCondition.SUNNY -> R.drawable.ic_sunny
//                                        WeatherCondition.CLOUDY -> R.drawable.ic_cloudy
//                                        WeatherCondition.RAINY -> R.drawable.ic_rainy
//                                        WeatherCondition.SNOWY -> R.drawable.ic_snowy
//                                        WeatherCondition.THUNDERSTORM -> R.drawable.ic_thunderstorm
//                                    }
//                                ),
//                                contentDescription = "Weather condition"
//                            )
                        }
                    }
                )
            }
        }
        if (viewState.isLoading) {
            CircularProgressBar()
        }
    }
}
