package ru.darf.weathercompose.domain.model

import com.google.gson.annotations.SerializedName

data class Cities(
    @SerializedName("results")
    val cities: List<City>,
)
