package com.example.binge.ui.activities.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.binge.R
import com.example.binge.databinding.ActivitySplashScreenBinding
import com.example.binge.ui.activities.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySplashScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.theme_shared_preference),
            Context.MODE_PRIVATE
        )
        val factory = SplashScreenViewModelFactory(sharedPref, this)
        val viewModel = ViewModelProvider(this, factory)
            .get(SplashScreenViewModel::class.java)

        viewModel.startIntent.observe(this, {
            if (it) {
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
                finish()
            }
        })

        viewModel.progressValue.observe(this, {
            binding.splashScreenProgressBar.setProgress(it, true)
        })

        viewModel.darkMode.observe(this, {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        })

    }

}