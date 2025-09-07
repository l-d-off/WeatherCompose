package ru.darf.weathercompose.domain.model

sealed interface NetworkState<out T> {
    data class Success<out T>(val data: T?) : NetworkState<T>
    data class Error(val error: Throwable) : NetworkState<Nothing>
    data class ServerError(val descriptionError: String) : NetworkState<Nothing>
}