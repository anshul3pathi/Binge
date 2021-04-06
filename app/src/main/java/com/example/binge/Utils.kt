package com.example.binge

import com.example.binge.model.data.GenreFeed
import com.example.binge.model.data.Movies
import java.lang.IllegalArgumentException

enum class DataFetchingStatus {
    LOADING,
    FAILED,
    DONE
}

enum class SortBy {
    RATING,
    ALPHABETS,
    YEAR
}

enum class CurrentTheme {
    LIGHT,
    DARK
}

fun convertMovieDataToFeedData(moviesData: List<Movies>): List<GenreFeed> {
    val feedData = mutableMapOf<String, MutableList<Movies>>()
    for (movie in moviesData) {
        for (genreItem in movie.genre) {
            var list = feedData[genreItem]
            if (list == null) {
                list = mutableListOf(movie)
            } else {
                list.add(movie)
            }
            feedData[genreItem] = list
        }
    }

    var i = 0L
    val tempFeedData = mutableListOf<GenreFeed>()
    for (feedItem in feedData) {
        tempFeedData.add(GenreFeed(i, feedItem.key, feedItem.value))
        i++
    }
    return tempFeedData
}

fun sortFeedData(value: SortBy, feedData: List<GenreFeed>): List<GenreFeed> {
    val tempList = mutableListOf<GenreFeed>()
    for(item in feedData) {
        tempList.add(
            GenreFeed(item.id, item.genre, when (value) {
                SortBy.YEAR -> {
                    item.movies.sortedByDescending { it.year }
                }
                SortBy.RATING -> {
                    item.movies.sortedByDescending { it.rating }
                }
                SortBy.ALPHABETS -> {
                    item.movies.sortedBy { it.movieName }
                }
                else -> {
                    item.movies.sortedBy { it.movieName }
                    throw IllegalArgumentException("Sort order cannot be determined")
                }
            })
        )
    }
    return tempList
}

fun randomNumberGenerator() = (0..Long.MAX_VALUE).random()
