package com.example.binge.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.IMDBApi
import com.example.binge.R
import com.example.binge.databinding.ActivityMainBinding
import com.example.binge.model.MovieRepository
import com.example.binge.model.data.Movies
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.navHostFragment)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val menuInflater = menuInflater
//        menuInflater.inflate(R.menu.menu_item, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
//        R.id.actionSortBy -> {
//            Log.d("Main Activity", "sort by action selected")
//            true
//        }
//        R.id.actionChangeTheme -> {
//            Log.d("Main Activity", "change theme action selected")
//            true
//        }
//        else -> {
//            super.onOptionsItemSelected(item)
//        }
//    }

}
