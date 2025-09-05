package ru.darf.weathercompose.data.local

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.darf.weathercompose.data.model.UserData
import javax.inject.Inject
import javax.inject.Singleton
import ru.darf.weathercompose.BuildConfig

@Singleton
class DataStorePrefs @Inject constructor(
    @param:ApplicationContext val context: Context,
) {
    private val Context.dataStore by dataStore(
        fileName = "local_data_${BuildConfig.APPLICATION_ID}.json",
        serializer = UserDataSerializer(CryptoManager(context))
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var hasUserData = false
        private set

    init {
        scope.launch {
            context.dataStore.data.collect {
                hasUserData = it.login != null
            }
        }
    }

    suspend fun updateUserData(
        onUpdate: (UserData) -> UserData,
    ) = context.dataStore.updateData { onUpdate(it) }

    suspend fun deleteUserData() {
        updateUserData { UserData() }
    }
}
