package com.example.binge.model

import androidx.lifecycle.LiveData
import com.example.binge.model.data.Movies

interface MoviesDataSource {

    suspend fun fetchAllData(): Result<List<Movies>>
    suspend fun deleteOldData()
    suspend fun saveNewData(list: List<Movies>)
    suspend fun getMoviesById(movieId: Long): Result<Movies>

}