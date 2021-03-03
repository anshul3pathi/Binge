package com.example.binge.model.database

import android.content.Context
import androidx.room.*
import com.example.binge.model.data.Movies

@Database(entities = [Movies::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract val moviesDataBaseDao : MoviesDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDataBase? = null

        fun getDatabase(context: Context): MoviesDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDataBase::class.java,
                    "covid19_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}