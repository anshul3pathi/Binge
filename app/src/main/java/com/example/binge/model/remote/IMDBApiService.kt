package com.example.binge

import com.example.binge.model.data.Movies
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/theapache64/top250/master/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface IMDBApiService {
    @GET("top250_min.json")
    suspend fun getMoviesData(): List<Movies>
}

object IMDBApi {
    val retrofitService: IMDBApiService by lazy { retrofit.create(IMDBApiService::class.java) }
}