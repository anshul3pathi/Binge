package com.example.binge.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.binge.IMDBApi
import com.example.binge.model.data.GenreFeed
import com.example.binge.model.data.Movies
import kotlinx.coroutines.*
import java.lang.Exception

const val LOG_TAG = "Movies Repository"

enum class DataFetchingStatus {
    LOADING,
    FAILED,
    DONE
}

class MovieRepository {

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.IO)
    private val rawData = mutableListOf<Movies>()

    private val _feedLiveData = MutableLiveData<List<GenreFeed>>()
    val feedLiveData: LiveData<List<GenreFeed>>
        get() = _feedLiveData

    private val _dataFetchingStatus = MutableLiveData<DataFetchingStatus>()
    val dataFetchingStatus: LiveData<DataFetchingStatus>
        get() = _dataFetchingStatus

    init {
        fetchRawData()
    }

    private fun processRawData() {
        val feedData = mutableMapOf<String, MutableList<Movies>>()
        for(movie in rawData) {
            for(genreItem in movie.genre) {
                var list = feedData[genreItem]
                if(list == null) {
                    list = mutableListOf()
                    list.add(movie)
                } else {
                    list.add(movie)
                }
                list.sortBy { it.movieName }
                feedData[genreItem] = list
            }
        }
        populateFeedData(feedData)
    }

    private fun populateFeedData(feedData: MutableMap<String, MutableList<Movies>>) {
        val feedDataList = mutableListOf<GenreFeed>()
        var i = 1L
        for(item in feedData) {
            feedDataList.add(GenreFeed(i, item.key, item.value))
            i++
        }
        uiScope.launch {
            feedDataList.sortBy { it.genre }
        }
        _feedLiveData.value = feedDataList
    }


    private fun fetchRawData() {
        _dataFetchingStatus.value = DataFetchingStatus.LOADING
        uiScope.launch {
            try {
                rawData.addAll(IMDBApi.retrofitService.getMoviesData())
                GlobalScope.launch(Dispatchers.Main) {
                    _dataFetchingStatus.value = DataFetchingStatus.DONE
                    processRawData()
                }
            } catch(e: Exception) {
                Log.e(LOG_TAG, "Couldn't fetch data. Error - ${e.message}")
                GlobalScope.launch(Dispatchers.Main) {
                    _dataFetchingStatus.value = DataFetchingStatus.FAILED
                }
            }
        }
    }
}