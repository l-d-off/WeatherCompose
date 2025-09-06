package ru.darf.weathercompose.data.local

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ru.darf.weathercompose.domain.model.UserData
import java.io.InputStream
import java.io.OutputStream

class UserDataSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<UserData> {

    override val defaultValue: UserData get() = UserData()

    override suspend fun readFrom(input: InputStream): UserData {
        return try {
            val text = input.reader().use { reader -> reader.readText() }
            val decode = cryptoManager.decrypt(text) ?: run { return defaultValue }
            Json.decodeFromString(
                deserializer = UserData.serializer(),
                string = decode
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        try {
            val jsonString = Json.encodeToString(
                serializer = UserData.serializer(),
                value = t
            )
            val encryptBytes = cryptoManager.encrypt(jsonString) ?: return
            output.also {
                it.write(encryptBytes.encodeToByteArray())
            }
        } catch (e: SerializationException) {
            e.printStackTrace()
        }
    }
}