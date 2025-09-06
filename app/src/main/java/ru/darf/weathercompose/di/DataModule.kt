package ru.darf.weathercompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.darf.weathercompose.data.local.AppDatabase
import ru.darf.weathercompose.data.local.dao.CityDao
import ru.darf.weathercompose.data.repository.UserDataRepositoryImpl
import ru.darf.weathercompose.data.repository.WeatherRepositoryImpl
import ru.darf.weathercompose.domain.repository.UserDataRepository
import ru.darf.weathercompose.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository = impl

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()

    @Provides
    @Singleton
    fun provideUserDataRepository(
        impl: UserDataRepositoryImpl,
    ): UserDataRepository = impl
}