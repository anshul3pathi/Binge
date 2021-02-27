package com.example.binge.ui.fragments.movie

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DetailMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _openImdbLiveData = MutableLiveData<Boolean>()
    val openImdbLiveData: LiveData<Boolean>
        get() = _openImdbLiveData

    init {
        _openImdbLiveData.value = false
    }

    fun imdbUrlOpened() {
        _openImdbLiveData.value = false
    }

    fun openImdbUrlButtonClicked() {
        _openImdbLiveData.value = true
    }

}