package com.example.binge.model

import android.util.Log
import com.example.binge.IMDBApi
import com.example.binge.model.data.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieRepository {

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.IO)
    private val rawData: List<Movies>
    private val differentGenres = HashSet<String>()

    init {
        rawData = fetchRawData()
        processRawData()
    }

    private fun processRawData() {
        for(movie in rawData) {
            for(genre in movie.genre) {
                differentGenres.add(genre)
            }
        }
    }

    fun getGenres(): Array<String> {
        return differentGenres.toTypedArray()
    }


    private fun fetchRawData(): List<Movies> {
        var rawData = listOf<Movies>()
        uiScope.launch {
            try {
                rawData = IMDBApi.retrofitService.getMoviesData()
            } catch(e: Exception) {
                Log.e("MovieRepository", "Couldn't fetch data. Error - ${e.message}")
            }
        }
        return rawData
    }
}