package com.example.binge.ui.fragments.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.binge.model.DataFetchingStatus
import com.example.binge.model.MovieRepository
import com.example.binge.model.data.GenreFeed

class MovieFeedViewModel(application: Application) : AndroidViewModel(application) {
    val feedLiveData: LiveData<List<GenreFeed>>
    val dataFetchingStatus: LiveData<DataFetchingStatus>

    private val movieRepository = MovieRepository()

    init {
        feedLiveData = movieRepository.feedLiveData
        dataFetchingStatus = movieRepository.dataFetchingStatus
    }
}