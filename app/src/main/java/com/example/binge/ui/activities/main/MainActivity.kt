package com.example.binge.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.binge.R
import com.example.binge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.navHostFragment)

        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
    }

}
