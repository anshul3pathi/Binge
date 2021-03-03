package com.example.binge.ui.fragments.feed

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieFeedViewModelFactory(private val activity: Activity) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieFeedViewModel::class.java)) {
            return MovieFeedViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}