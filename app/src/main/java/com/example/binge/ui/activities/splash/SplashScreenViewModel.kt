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
import com.example.binge.model.storage.Storage
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(sharedPref: Storage) : ViewModel() {

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
        _darkMode.value = sharedPref.getBoolean()
        Log.d("SplashScreenViewModel", "${_darkMode.value}")
        _startIntent.value = false
        _progressValue.value = 0
        startTimer()
    }

    private fun startTimer() {

        val inFuture = 1000L
        val countDownInterval = 1000L

        val timer = object : CountDownTimer(inFuture, countDownInterval) {
            override fun onTick(p0: Long) {
                val progressValue = ((100 * countDownInterval) / inFuture).toInt()
                _progressValue.value =
                    _progressValue.value?.plus(progressValue)
            }

            override fun onFinish() {
                _startIntent.value = true
            }

        }

        timer.start()
    }

}