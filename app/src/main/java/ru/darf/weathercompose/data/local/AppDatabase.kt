package ru.darf.weathercompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.darf.weathercompose.data.local.dao.CityDao
import ru.darf.weathercompose.data.local.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .createFromAsset("databases/cities.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}