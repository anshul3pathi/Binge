package com.example.binge.model.database

import com.example.binge.model.MoviesDataSource
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import javax.inject.Inject
import kotlin.Exception

class LocalDataSource @Inject constructor(private val moviesDao: MoviesDao) : MoviesDataSource {

    override suspend fun fetchAllData(): Result<List<Movies>> {
        val moviesData = moviesDao.getAll()
        return if(moviesData.isEmpty()) {
            Result.Error(Exception("Database is empty"))
        } else {
            Result.Success(moviesData)
        }
    }

    override suspend fun deleteOldData() {
        moviesDao.deleteOldData()
    }

    override suspend fun saveNewData(list: List<Movies>) {
        moviesDao.insertAll(list)
    }

    override suspend fun getMoviesById(movieId: Long): Result<Movies> {
        val movies = moviesDao.getMovieById(movieId)
        return if(movies != null) {
            Result.Success(movies)
        } else {
            Result.Error(Exception("Cannot fetch movie by movie id!"))
        }
    }

}