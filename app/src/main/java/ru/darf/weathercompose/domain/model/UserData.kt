package ru.darf.weathercompose.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val login: String? = null,
    val password: String? = null,
)