package com.example.binge.data.source

import com.example.binge.model.MoviesDataSource
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import kotlin.Exception

class FakeDataSource(
    movies: MutableList<Movies>,
    asRemote: Boolean
) : MoviesDataSource {

    private var shouldFetchError = false

    private val fakeDataBase = movies

    private val delay: Long = if (asRemote) {
        1000L
    } else {
        0L
    }

    override suspend fun fetchAllData(): Result<List<Movies>> {
        Thread.sleep(delay)
        return when (!shouldFetchError) {
            true -> {
                Result.Success(fakeDataBase)
            }
            false -> {
                Result.Error(IllegalStateException("Cannot fetch fake data!"))
            }
        }
    }

    override suspend fun deleteOldData() {
        fakeDataBase.clear()
    }

    override suspend fun saveNewData(list: List<Movies>) {
        fakeDataBase.addAll(list)
    }

    override suspend fun getMoviesById(movieId: Long): Result<Movies> {
        return if(!shouldFetchError) {
            Result.Success(fakeDataBase[0])
        } else {
            Result.Error(Exception("Cannot fetch movie by id!"))
        }
    }

    fun setShouldFetchError(value: Boolean) {
        shouldFetchError = value
    }
}