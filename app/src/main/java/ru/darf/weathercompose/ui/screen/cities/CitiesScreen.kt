package ru.darf.weathercompose.ui.screen.cities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.compose.rememberState
import ru.darf.weathercompose.core.router.ScreenCompanionRouter
import ru.darf.weathercompose.core.ui.CircularProgressBar
import ru.darf.weathercompose.core.ui.TopBar
import ru.darf.weathercompose.domain.model.City

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
                title = stringResource(R.string.cities_screen_title),
                onBackClick = {
                    viewModel.popBackStack(navController)
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.updateOpenSearchCityDialog(true)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add city"
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
            if (viewState.localCities.isNotEmpty()) {
                items(
                    items = viewState.localCities,
                    key = { city -> city.id }
                ) { city ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.deleteCity(city)
                            }
                            true
                        }
                    )
                    SwipeToDismissBox(
                        modifier = Modifier.animateItem(),
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CardDefaults.shape)
                                    .background(Color.Red)
                                    .wrapContentSize(Alignment.CenterEnd)
                                    .padding(end = 8.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete city",
                                tint = Color.White
                            )
                        }
                    ) {
                        Card(
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = city.name,
                                        fontSize = 20.sp
                                    )
                                    if (city.region.isNotBlank()) {
                                        Text(
                                            text = city.region,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.cities_screen_stub_empty_cities),
                        fontSize = 20.sp
                    )
                }
            }
        }
        if (viewState.isLoading) {
            CircularProgressBar()
        }
    }
    if (viewState.openSearchCityDialog) {
        SearchCitiesDialog(
            viewState = viewState,
            onDismiss = {
                viewModel.updateOpenSearchCityDialog(false)
            },
            onUpdateSearchText = viewModel::updateSearchText,
            onSelectCity = viewModel::insertCity
        )
    }
}

@Composable
private fun SearchCitiesDialog(
    viewState: CitiesViewState,
    onDismiss: () -> Unit,
    onUpdateSearchText: (TextFieldValue) -> Unit,
    onSelectCity: (City) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(8.dp)),
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            topBar = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.searchText,
                    onValueChange = onUpdateSearchText,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.cities_screen_placeholder_search),
                            color = Color.LightGray
                        )
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search field"
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
                if (viewState.searchCities.isNotEmpty()) {
                    items(
                        items = viewState.searchCities,
                    ) { city ->
                        Card(
                            modifier = Modifier.animateItem(),
                            onClick = {
                                onSelectCity(city)
                                onDismiss()
                            },
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = city.name,
                                        fontSize = 20.sp
                                    )
                                    if (city.region.isNotBlank()) {
                                        Text(
                                            text = city.region,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        )
                    }
                } else {
                    item {
                        Text(
                            text = stringResource(R.string.cities_screen_stub_empty_search),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}