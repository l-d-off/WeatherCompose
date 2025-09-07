package ru.darf.weathercompose.domain.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    val weatherToday: WeatherToday,
)

data class WeatherToday(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("temperature_2m")
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
) {
    fun getWeatherCondition(): WeatherCondition {
        return WeatherCondition.getWeatherConditionByCode(weatherCode)
    }
}

enum class WeatherCondition {
    SUNNY,
    CLOUDY,
    RAINY,
    SNOWY,
    THUNDERSTORM;

    companion object {
        fun getWeatherConditionByCode(weatherCode: Int): WeatherCondition {
            return when (weatherCode) {
                0 -> SUNNY
                1, 2, 3, 45, 48 -> CLOUDY
                51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> RAINY
                71, 73, 75, 77, 85, 86 -> SNOWY
                95, 96, 99 -> THUNDERSTORM
                else -> SUNNY
            }
        }
    }
}