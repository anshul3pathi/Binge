package com.example.binge.model.data

import com.squareup.moshi.Json

data class Movies(
    val actors: List<String>,
    val desc: String,
    val directors: List<String>,
    val genre: List<String>,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "thumb_url") val thumbNailUrl: String,
    @Json(name = "imdb_url") val imdbUrl: String,
    @Json(name = "name") val movieName: String,
    val rating: Double,
    val year: Int
)
