package com.example.binge.ui.activities.splash

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.binge.model.storage.Storage
import com.example.binge.ui.fragments.feed.MovieFeedViewModel
import javax.inject.Inject

class SplashScreenViewModelFactory @Inject constructor(
    private val sharedPref: Storage) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(sharedPref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}