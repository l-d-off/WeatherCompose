package ru.darf.weathercompose.ui.screen.auth

import androidx.compose.ui.text.input.TextFieldValue

data class AuthViewState(
    val login: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
)
