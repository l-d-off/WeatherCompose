package ru.darf.weathercompose.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.darf.weathercompose.domain.model.Cities
import ru.darf.weathercompose.domain.model.Weather

interface WeatherApi {

    @GET("https://api.open-meteo.com/v1/forecast")
    suspend fun getWeathers(
        @Query("latitude") latitudeString: String,
        @Query("longitude") longitudeString: String,
        @Query("timezone") timezone: String = "Europe/Moscow",
        @Query("current") current: String = "temperature_2m,weather_code",
    ): Response<List<Weather>>

    @GET("https://geocoding-api.open-meteo.com/v1/search")
    suspend fun getCities(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "ru",
        @Query("countryCode") countryCode: String = "RU",
    ): Response<Cities>
}