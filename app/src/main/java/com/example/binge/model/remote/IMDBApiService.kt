package com.example.binge.model.remote

import com.example.binge.model.data.Movies
import retrofit2.http.GET

interface IMDBApiService {

    @GET("top250_min.json")
    suspend fun getMoviesData(): List<Movies>

}