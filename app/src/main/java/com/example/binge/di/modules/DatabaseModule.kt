package com.example.binge.di.modules

import android.content.Context
import androidx.room.Room
import com.example.binge.model.database.MoviesDao
import com.example.binge.model.database.MoviesDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDataBase {
        return Room.databaseBuilder(context, MoviesDataBase::class.java, "moviesDb")
            .build()
    }

    @Provides
    fun provideMovieDao(moviesDataBase: MoviesDataBase): MoviesDao {
        return moviesDataBase.moviesDao()
    }

}