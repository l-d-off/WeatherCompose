package ru.darf.weathercompose.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.darf.weathercompose.domain.model.UserData

interface UserDataRepository {
    fun getUserData(): Flow<UserData>
    suspend fun updateUserData(onUpdate: (UserData) -> UserData)
    suspend fun deleteUserData()
}