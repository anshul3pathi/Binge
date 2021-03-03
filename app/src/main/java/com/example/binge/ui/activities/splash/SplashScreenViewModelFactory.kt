package com.example.binge.ui.activities.splash

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.binge.ui.fragments.feed.MovieFeedViewModel

class SplashScreenViewModelFactory(
    private val sharedPref: SharedPreferences,
    private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(sharedPref, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}