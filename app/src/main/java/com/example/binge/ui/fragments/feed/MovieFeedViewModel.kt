package com.example.binge.ui.fragments.feed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binge.*
import com.example.binge.model.data.GenreFeed
import com.example.binge.model.data.Movies
import com.example.binge.model.repository.MoviesRepo
import com.example.binge.model.storage.Storage
import kotlinx.coroutines.launch
import com.example.binge.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieFeedViewModel @Inject constructor(
    private val moviesRepository: MoviesRepo,
    private val sharedPreferencesStorage: Storage
) : ViewModel() {

    private var sortBy = SortBy.ALPHABETS

    private val _sortByToast = MutableLiveData<String>()
    val sortByToast = _sortByToast

    private val _feedData = MutableLiveData<List<GenreFeed>>()
    val feedData = _feedData

    private val _dataFetchingStatus = MutableLiveData(DataFetchingStatus.LOADING)
    val dataFetchingStatus = _dataFetchingStatus

    init {
        populateFeedData()
    }

    private fun populateFeedData() {
        viewModelScope.launch {
            val moviesData = moviesRepository.getMovies()
            withContext(Dispatchers.Main) {
                if(moviesData is Result.Success) {
                    _dataFetchingStatus.value = DataFetchingStatus.DONE
//                    convertMovieDataToFeedData(moviesData.data)
                    _feedData.value =
                        sortFeedData(sortBy, convertMovieDataToFeedData(moviesData.data))
//                    sortFeedData()
                } else if (moviesData is Result.Error){
                    _dataFetchingStatus.value = DataFetchingStatus.FAILED
                }
            }
        }
    }

    fun refreshData() {
        populateFeedData()
    }

    fun saveThemePreference(currentTheme: CurrentTheme) {
        when (currentTheme) {
            CurrentTheme.LIGHT -> {
                sharedPreferencesStorage.setBoolean(false)
//                Log.d("MovieFeedViewModel", "theme preference to false")
            }
            CurrentTheme.DARK -> {
                sharedPreferencesStorage.setBoolean(true)
//                Log.d("MovieFeedViewModel", "theme preference to true")
            }
        }
    }

    fun getCurrentTheme(): CurrentTheme {
        return when(sharedPreferencesStorage.getBoolean()) {
            true -> CurrentTheme.DARK
            false -> CurrentTheme.LIGHT
        }
    }

    fun changeSortByType(sortBy: SortBy) {
        if(sortBy != this.sortBy) {
            this.sortBy = sortBy
            when (sortBy) {
                SortBy.YEAR -> {
                    _sortByToast.value = "Sorting by year."
                }
                SortBy.RATING -> {
                    _sortByToast.value = "Sorting by rating."
                }
                SortBy.ALPHABETS -> {
                    _sortByToast.value = "Sorting by movie name."
                }
            }
            _feedData.value = sortFeedData(sortBy, _feedData.value!!)
        }
    }

}