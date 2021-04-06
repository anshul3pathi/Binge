package com.example.binge.ui.activities.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.binge.BingeApplication
import com.example.binge.R
import com.example.binge.databinding.ActivitySplashScreenBinding
import com.example.binge.ui.activities.main.MainActivity
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: SplashScreenViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BingeApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ActivitySplashScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        val viewModel by viewModels<SplashScreenViewModel> { factory }

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