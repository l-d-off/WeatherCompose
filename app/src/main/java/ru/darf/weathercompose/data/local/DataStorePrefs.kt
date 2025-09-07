package ru.darf.weathercompose.data.local

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.darf.weathercompose.BuildConfig
import ru.darf.weathercompose.domain.model.UserData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStorePrefs @Inject constructor(
    @param:ApplicationContext val context: Context,
) {
    private val Context.dataStore by dataStore(
        fileName = "local_data_${BuildConfig.APPLICATION_ID}.json",
        serializer = UserDataSerializer(CryptoManager(context))
    )

    fun getUserDataFlow() = context.dataStore.data

    suspend fun updateUserData(
        onUpdate: (UserData) -> UserData,
    ) = context.dataStore.updateData { onUpdate(it) }

    suspend fun deleteUserData() {
        updateUserData { UserData() }
    }
}
