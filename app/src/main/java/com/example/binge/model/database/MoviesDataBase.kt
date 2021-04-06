package com.example.binge.model.database

import android.content.Context
import androidx.room.*
import com.example.binge.model.data.Movies

@Database(entities = [Movies::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}