package com.example.binge.model.data

data class GenreFeed(
        val id: Long,
        val genre: String,
        val movies: List<Movies>
)
