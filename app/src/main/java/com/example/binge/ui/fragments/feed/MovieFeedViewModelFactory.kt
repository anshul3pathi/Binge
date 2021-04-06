package com.example.binge.ui.fragments.feed

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.binge.model.repository.MoviesRepo
import com.example.binge.model.storage.Storage
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieFeedViewModelFactory @Inject constructor(
    private val movieRepository: MoviesRepo,
    private  val sharedPref: Storage
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieFeedViewModel::class.java)) {
            return MovieFeedViewModel(movieRepository, sharedPref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}