package com.example.binge.ui.fragments.feed

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.binge.CurrentTheme
import com.example.binge.DataFetchingStatus
import com.example.binge.R
import com.example.binge.SortBy
import com.example.binge.model.MovieRepository
import com.example.binge.model.data.GenreFeed
import com.example.binge.model.database.MoviesDataBase
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieFeedViewModel(private val activity: Activity) :
        ViewModel() {

    val feedLiveDataAlpha: LiveData<List<GenreFeed>>

    val feedLiveDataRating: LiveData<List<GenreFeed>>

    val feedLiveDataYear: LiveData<List<GenreFeed>>

    val dataFetchingStatus: LiveData<DataFetchingStatus>

    private val _changeThemeTriggered = MutableLiveData<Boolean>()
    val changeThemeTriggered: LiveData<Boolean>
        get() = _changeThemeTriggered

    private val _sortBy = MutableLiveData<SortBy>()
    val sortBy: LiveData<SortBy>
        get() = _sortBy


    private val moviesDataBaseDao = MoviesDataBase
        .getDatabase(activity.application).moviesDataBaseDao

    private val movieRepository = MovieRepository(moviesDataBaseDao, activity)

    private var _currentTheme: CurrentTheme
    val currentTheme: CurrentTheme
        get() = _currentTheme

    private val sharedPref = activity.getSharedPreferences(
        activity.applicationContext.getString(R.string.theme_shared_preference),
        Context.MODE_PRIVATE
    )


    init {
        feedLiveDataAlpha = movieRepository.feedLiveDataAlpha
        feedLiveDataRating = movieRepository.feedLiveDataRating
        feedLiveDataYear = movieRepository.feedLiveDataYear

        dataFetchingStatus = movieRepository.dataFetchingStatus

        val themeValue = sharedPref.getBoolean(
            activity.applicationContext.getString(R.string.dark_theme_enabled_key),
            false
        )
        _currentTheme = if (themeValue) {
            CurrentTheme.DARK
        } else {
            CurrentTheme.LIGHT
        }
        _sortBy.value = SortBy.ALPHABETS
    }

    fun changeTheme() {
        _changeThemeTriggered.value = true
        Log.d("MovieFeedViewModel", "$_currentTheme")
    }

    fun themeChanged() {
        _changeThemeTriggered.value = false
        if (_currentTheme == CurrentTheme.LIGHT) {
            _currentTheme = CurrentTheme.DARK
            sharedPref.edit {
                putBoolean(
                    activity.applicationContext.getString(R.string.dark_theme_enabled_key),
                    true
                )
            }
            Log.d("MovieFeedViewModel", "dark mode - true")
        } else {
            _currentTheme = CurrentTheme.LIGHT
            sharedPref.edit {
                putBoolean(
                    activity.applicationContext.getString(R.string.dark_theme_enabled_key),
                    false
                )
            }
            Log.d("MovieFeedViewModel", "dark mode - false")
        }

    }

    fun changeSortByType(sortOrder: SortBy) {
        _sortBy.value = sortOrder
    }
}