package ru.darf.weathercompose.domain.model

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("admin1")
    val region: String,
)
