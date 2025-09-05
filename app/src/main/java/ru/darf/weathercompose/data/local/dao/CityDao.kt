package ru.darf.weathercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.darf.weathercompose.data.local.entity.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    suspend fun getAll(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)
}