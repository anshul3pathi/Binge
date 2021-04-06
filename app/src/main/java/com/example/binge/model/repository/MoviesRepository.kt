package com.example.binge.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.binge.di.modules.LocalDataSource
import com.example.binge.di.modules.RemoteDataSource
import com.example.binge.model.MoviesDataSource
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import com.example.binge.model.storage.Storage
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
class MoviesRepository @Inject constructor(
    @LocalDataSource private val localDataSource: MoviesDataSource,
    @RemoteDataSource private val remoteDataSource: MoviesDataSource,
    private val sharedPref: Storage,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepo {

//    @Inject
//    @field:LocalDataSource lateinit var localDataSource: MoviesDataSource

//    @Inject
//    @field:RemoteDataSource lateinit var remoteDataSource: MoviesDataSource

    companion object {
        private val DATA_SYNC_TIMEOUT = 1.hours.inMilliseconds.toLong()
    }

     override suspend fun getMovies(): Result<List<Movies>> {
        val deterrent = shouldFetchNewData()
         return if(deterrent) {
             val moviesData = fetchNewData()
             if(moviesData is Result.Success) {  //  fetch from remote
                 saveNewData(moviesData.data)
                 fetchFromDb()
             } else {  //  if fetching from remote fails, fetch from local
                 fetchFromDb()
             }
         } else {  //  fetch from local
             val result = fetchFromDb()
             return if (result is Result.Success) {  //  if fetching from local is success then return
                 result
             } else {  //  otherwise try fetching from remote
                 fetchNewData()
             }
         }
    }

    override fun shouldFetchNewData(): Boolean {
        val lastSynced = sharedPref.getLong()
        return lastSynced == -1L || lastSynced - DATA_SYNC_TIMEOUT >= 1
    }

    override suspend fun fetchNewData(): Result<List<Movies>> {
//        Log.d("MoviesRepository", "fetching new data")
        sharedPref.setLong(System.currentTimeMillis())
        return remoteDataSource.fetchAllData()
    }

    override suspend fun saveNewData(list: List<Movies>) {
        deleteOldData()
        localDataSource.saveNewData(list)
    }

    override suspend fun fetchFromDb(): Result<List<Movies>> {
//        Log.d("MoviesRepository", "fetching old data")
        return localDataSource.fetchAllData()
    }

    override suspend fun getMovieById(id: Long): Result<Movies> {
        return localDataSource.getMoviesById(id)
    }

    override suspend fun deleteOldData() {
//        Log.d("MoviesRepository", "deleting old data")
        localDataSource.deleteOldData()
    }
}