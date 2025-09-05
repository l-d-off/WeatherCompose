package ru.darf.weathercompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val login: String? = null,
    val password: String? = null,
)