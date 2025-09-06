package ru.darf.weathercompose.domain.model

data class City(
    val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
