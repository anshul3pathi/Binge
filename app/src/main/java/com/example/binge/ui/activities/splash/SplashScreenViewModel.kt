package com.example.binge.ui.activities.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.binge.R

class SplashScreenViewModel(sharedPref: SharedPreferences, context: Context) : ViewModel() {

    private val _startIntent = MutableLiveData<Boolean>()
    val startIntent: LiveData<Boolean>
        get() = _startIntent

    private val _progressValue = MutableLiveData<Int>()
    val progressValue: LiveData<Int>
        get() = _progressValue

    private val _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean>
        get() = _darkMode


    init {
        _darkMode.value = sharedPref.getBoolean(
            context.getString(R.string.dark_theme_enabled_key),
            false
        )
        Log.d("SplashScreenViewModel", "${_darkMode.value}")
        _startIntent.value = false
        _progressValue.value = 0
        startTimer()
    }

    private fun startTimer() {

        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(p0: Long) {
                _progressValue.value = _progressValue.value?.plus(20)
            }

            override fun onFinish() {
                _startIntent.value = true
            }

        }

        timer.start()
    }

}