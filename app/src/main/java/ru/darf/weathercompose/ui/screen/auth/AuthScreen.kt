package ru.darf.weathercompose.ui.screen.auth

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import ru.darf.weathercompose.R
import ru.darf.weathercompose.core.logger.logE
import ru.darf.weathercompose.core.router.ScreenCompanionRouter
import ru.darf.weathercompose.core.ui.TopBar

class AuthScreen(
    private val navBuilder: NavGraphBuilder,
    private val navController: NavHostController,
) {
    companion object : ScreenCompanionRouter()

    fun addScreen() = navBuilder.composable(route) { entry ->

        val viewModel = hiltViewModel<AuthViewModel>(entry)
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        AuthContent(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AuthContent(
    viewModel: AuthViewModel,
    viewState: AuthViewState,
    navController: NavHostController,
) {

    val context = LocalContext.current
    val topSpaceDp = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp() / 3
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color.Unspecified,
        contentColor = Color.Unspecified,
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_screen_title)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(topSpaceDp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.login,
                    onValueChange = viewModel::updateLogin,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_screen_placeholder_login),
                        )
                    },
                    singleLine = true,
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.password,
                    onValueChange = viewModel::updatePassword,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_screen_placeholder_password),
                        )
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.goToWeatherScreen(
                            context = context,
                            navController = navController
                        )
                    },
                    content = {
                        Text(
                            text = stringResource(R.string.auth_screen_button_sign_in),
                        )
                    }
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
