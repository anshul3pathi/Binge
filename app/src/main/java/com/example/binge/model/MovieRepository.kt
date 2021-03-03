package com.example.binge.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.binge.DataFetchingStatus
import com.example.binge.IMDBApi
import com.example.binge.SortBy
import com.example.binge.model.data.GenreFeed
import com.example.binge.model.data.Movies
import com.example.binge.model.database.MoviesDataBaseDao
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.hours

const val LOG_TAG = "Movies Repository"

@ExperimentalTime
class MovieRepository(private val moviesDataBaseDao: MoviesDataBaseDao,
                      private val activity: Activity) {

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.IO)
    private val fetchedData = mutableListOf<Movies>()

    private val _feedLiveDataAlpha = MutableLiveData<List<GenreFeed>>()
    val feedLiveDataAlpha: LiveData<List<GenreFeed>>
        get() = _feedLiveDataAlpha

    private val _feedLiveDataRating = MutableLiveData<List<GenreFeed>>()
    val feedLiveDataRating: LiveData<List<GenreFeed>>
        get() = _feedLiveDataRating

    private val _feedLiveDataYear = MutableLiveData<List<GenreFeed>>()
    val feedLiveDataYear: LiveData<List<GenreFeed>>
        get() = _feedLiveDataYear

    private val _dataFetchingStatus = MutableLiveData<DataFetchingStatus>()
    val dataFetchingStatus: LiveData<DataFetchingStatus>
        get() = _dataFetchingStatus

    private val parentFeedData = mutableListOf<GenreFeed>()

    private lateinit var sharedPref: SharedPreferences


    companion object {
        private val UPDATE_THRESHOLD = 1.hours.inMilliseconds.toLong()
        private const val LAST_SYNCED_KEY = "lastUpdateTime"
    }

    init {
        shouldFetchData()
    }

    private fun shouldFetchData() {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val lastSynced = sharedPref.getLong(LAST_SYNCED_KEY, -1)
        Log.d(LOG_TAG, "$lastSynced")

        val currentTime = System.currentTimeMillis()

        _dataFetchingStatus.value = DataFetchingStatus.LOADING

        if(currentTime - lastSynced >= UPDATE_THRESHOLD || lastSynced == -1L) {
            fetchNewData()
        } else {
            fetchFromDataBase()
        }
    }

    private fun fetchNewData() {
        Log.d(LOG_TAG, "fetching new data")
        uiScope.launch {
            try {
                fetchedData.addAll(IMDBApi.retrofitService.getMoviesData())
                GlobalScope.launch(Dispatchers.Main) {
                    _dataFetchingStatus.value = DataFetchingStatus.DONE
                    updateDataBase()
                }
            } catch(e: Exception) {
                Log.e(LOG_TAG, "Couldn't fetch data. Error - ${e.message}")
                GlobalScope.launch(Dispatchers.Main) {
                    _dataFetchingStatus.value = DataFetchingStatus.FAILED
                    fetchFromDataBase()
                }
            }
        }
    }

    private fun updateDataBase() {

        Log.d(LOG_TAG, "updating database")

        sharedPref.edit {
            putLong(LAST_SYNCED_KEY, System.currentTimeMillis())
        }

        uiScope.launch {
            moviesDataBaseDao.deleteOldData()
            moviesDataBaseDao.insertAll(fetchedData)
            withContext(Dispatchers.Main) {
                convertDataToFeedData()
            }
        }
    }

    private fun fetchFromDataBase() {
        Log.d(LOG_TAG, "fetching from database")
        uiScope.launch {
            val dataFromDb = moviesDataBaseDao.getAll()
            if (dataFromDb == null && _dataFetchingStatus.value == DataFetchingStatus.LOADING) {
                fetchNewData()
            } else if (dataFromDb == null &&
                _dataFetchingStatus.value == DataFetchingStatus.FAILED) {
                Log.e(LOG_TAG, "Cannot fetch new data - Error!")
            } else {
                fetchedData.clear()
                fetchedData.addAll(dataFromDb!!)
                withContext(Dispatchers.Main) {
                    _dataFetchingStatus.value = DataFetchingStatus.DONE
                    convertDataToFeedData()
                }
            }
        }
    }

    private fun convertDataToFeedData() {
        val feedData = mutableMapOf<String, MutableList<Movies>>()
        for (movie in fetchedData) {
            for (genreItem in movie.genre) {
                var list = feedData[genreItem]
                if(list == null) {
                    list = mutableListOf(movie)
                } else {
                    list.add(movie)
                }
                feedData[genreItem] = list
            }
        }

        var i = 0L
        for (feedItem in feedData) {
            parentFeedData.add(GenreFeed(i, feedItem.key, feedItem.value))
            i++
        }
        populateSortByDataFromParentFeedData()
    }

    private fun populateSortByDataFromParentFeedData() {
        for (sortBy in SortBy.values()) {
            when (sortBy) {
                SortBy.ALPHABETS -> {
                    val tempList = mutableListOf<GenreFeed>()
                    for (item in parentFeedData) {
                        tempList.add(
                            GenreFeed(
                            item.id,
                            item.genre,
                            item.movies.sortedBy { it.movieName })
                        )
                    }
                    _feedLiveDataAlpha.value = tempList
                }
                SortBy.RATING -> {
                    val tempList = mutableListOf<GenreFeed>()
                    for (item in parentFeedData) {
                        tempList.add(
                            GenreFeed(
                                item.id,
                                item.genre,
                                item.movies.sortedByDescending { it.rating })
                        )
                    }
                    _feedLiveDataRating.value = tempList
                }
                else -> {
                    val tempList = mutableListOf<GenreFeed>()
                    for (item in parentFeedData) {
                        tempList.add(
                            GenreFeed(
                                item.id,
                                item.genre,
                                item.movies.sortedByDescending { it.year })
                        )
                    }
                    _feedLiveDataYear.value = tempList
                }
            }
        }
    }

}