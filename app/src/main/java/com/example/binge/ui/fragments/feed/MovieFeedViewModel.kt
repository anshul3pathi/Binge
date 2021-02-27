package com.example.binge.ui.fragments.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.binge.model.DataFetchingStatus
import com.example.binge.model.MovieRepository
import com.example.binge.model.SortBy
import com.example.binge.model.data.GenreFeed

class MovieFeedViewModel(application: Application) : AndroidViewModel(application) {

    val feedLiveDataAlpha: LiveData<List<GenreFeed>>
    val feedLiveDataRating: LiveData<List<GenreFeed>>
    val feedLiveDataYear: LiveData<List<GenreFeed>>

    val dataFetchingStatus: LiveData<DataFetchingStatus>

    private val _darkModeStatus = MutableLiveData<Boolean>()
    val darkModeStatus: LiveData<Boolean>
        get() = _darkModeStatus

    private val _sortBy = MutableLiveData<SortBy>()
    val sortBy: LiveData<SortBy>
        get() = _sortBy

    private val movieRepository = MovieRepository()


    init {
        feedLiveDataAlpha = movieRepository.feedLiveDataAlpha
        feedLiveDataRating = movieRepository.feedLiveDataRating
        feedLiveDataYear = movieRepository.feedLiveDataYear

        dataFetchingStatus = movieRepository.dataFetchingStatus
        _darkModeStatus.value = false
        _sortBy.value = SortBy.ALPHABETS
    }

    fun changeDarkModeStatus(value: Boolean) {
        _darkModeStatus.value = !value
    }

    fun changeSortByType(sortOrder: SortBy) {
        _sortBy.value = sortOrder
    }
}