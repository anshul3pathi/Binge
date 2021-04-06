package com.example.binge.ui.fragments.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import com.example.binge.model.repository.MoviesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DetailMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _openImdbLiveData = MutableLiveData(false)
    val openImdbLiveData: LiveData<Boolean>
        get() = _openImdbLiveData

    fun imdbUrlOpened() {
        _openImdbLiveData.value = false
    }

    fun openImdbUrlButtonClicked() {
        _openImdbLiveData.value = true
    }

}