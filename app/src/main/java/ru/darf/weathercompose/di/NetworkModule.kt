package ru.darf.weathercompose.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.darf.weathercompose.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.open-meteo.com/"
    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 20L
    private const val WRITE_TIMEOUT = 20L
    private val IS_DEBUG = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(ApiLogger()).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        if (IS_DEBUG) {
            addInterceptor(httpLoggingInterceptor)
        }
    }.build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}

private class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "ApiLogger"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(JsonParser.parseString(message))
                Log.i(logName, prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Log.i(logName, message)
            }
        } else {
            Log.i(logName, message)
            return
        }
    }
}