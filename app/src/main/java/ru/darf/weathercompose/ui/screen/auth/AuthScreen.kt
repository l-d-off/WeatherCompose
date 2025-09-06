package ru.darf.weathercompose.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.darf.weathercompose.R
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

@Composable
private fun AuthContent(
    viewModel: AuthViewModel,
    viewState: AuthViewState,
    navController: NavHostController,
) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Unspecified,
        contentColor = Color.Unspecified,
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_screen_title)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(192.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.login,
                onValueChange = viewModel::updateLogin,
                placeholder = {
                    Text(
                        text = stringResource(R.string.auth_screen_placeholder_login),
                        color = Color.LightGray
                    )
                },
                maxLines = 1
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.password,
                onValueChange = viewModel::updatePassword,
                placeholder = {
                    Text(
                        text = stringResource(R.string.auth_screen_placeholder_password),
                        color = Color.LightGray
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
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
                        color = Color.White
                    )
                }
            )
        }
    }
}
