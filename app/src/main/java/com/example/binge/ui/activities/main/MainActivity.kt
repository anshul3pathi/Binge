package com.example.binge.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.IMDBApi
import com.example.binge.R
import com.example.binge.model.MovieRepository
import com.example.binge.model.data.Movies
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        var moviesList = listOf<Movies>()
//        val adapter = MoviesAdapter()
//        recyclerView.adapter = adapter
//        GlobalScope.launch {
//            try {
//                moviesList = IMDBApi.retrofitService.getMoviesData()
//                Log.d("MainActivity", "Size of data - ${moviesList.size}")
//                adapter.updateData(moviesList)
//            } catch(e: Exception) {
//                Log.e("MainActivity", "Error - ${e.message}")
//            }
//        }

//        val repo = MovieRepository()
//        repo.feedLiveData.observe(this, {genreList ->
//            Log.d("Main Activity", "Number of genres - ${genreList.size}")
//        })

    }
}