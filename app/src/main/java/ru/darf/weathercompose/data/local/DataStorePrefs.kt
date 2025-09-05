package ru.darf.weathercompose.data.local

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import ru.darf.weathercompose.BuildConfig
import ru.darf.weathercompose.data.model.UserData
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

    suspend fun updateUserData(
        onUpdate: (UserData) -> UserData,
    ) = context.dataStore.updateData { onUpdate(it) }

    suspend fun hasUserData() = context.dataStore.data.first().login != null

    suspend fun deleteUserData() {
        updateUserData { UserData() }
    }
}
