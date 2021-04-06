package com.example.binge.model.remote

import com.example.binge.model.MoviesDataSource
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val imdb: IMDBApiService) : MoviesDataSource {

    override suspend fun fetchAllData(): Result<List<Movies>> {
        return try {
            val fetchedData = imdb.getMoviesData()
            Result.Success(fetchedData)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun deleteOldData() {
        // No implementation needed
    }

    override suspend fun saveNewData(list: List<Movies>) {
        // No implementation needed
    }

    override suspend fun getMoviesById(movieId: Long): Result<Movies> {
        TODO("No need to implement")
    }
}