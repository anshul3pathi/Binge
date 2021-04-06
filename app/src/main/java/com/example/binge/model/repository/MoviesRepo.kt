package com.example.binge.model.repository

import androidx.lifecycle.LiveData
import com.example.binge.model.Result
import com.example.binge.model.data.Movies

interface MoviesRepo {
    suspend fun getMovies(): Result<List<Movies>>
    fun shouldFetchNewData(): Boolean
    suspend fun fetchNewData(): Result<List<Movies>>
    suspend fun saveNewData(list: List<Movies>)
    suspend fun fetchFromDb(): Result<List<Movies>>
    suspend fun getMovieById(id: Long): Result<Movies>
    suspend fun deleteOldData()
}