package ru.darf.weathercompose.data.repository

import kotlinx.coroutines.flow.Flow
import ru.darf.weathercompose.data.local.DataStorePrefs
import ru.darf.weathercompose.domain.model.UserData
import ru.darf.weathercompose.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val prefs: DataStorePrefs,
) : UserDataRepository {

    override fun getUserData(): Flow<UserData> {
        return prefs.getUserDataFlow()
    }

    override suspend fun updateUserData(onUpdate: (UserData) -> UserData) {
        prefs.updateUserData(onUpdate)
    }
}