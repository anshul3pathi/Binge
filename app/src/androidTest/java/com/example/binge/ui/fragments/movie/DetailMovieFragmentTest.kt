package com.example.binge.ui.fragments.movie

import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.openLinkWithText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.binge.R
import com.example.binge.model.data.Movies
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class DetailMovieFragmentTest {

    private val movie1 = Movies(
        listOf("Ayushman", "Tabbu", "Radhika"),
        "Kafi sexy movie",
        listOf("Dont know", "Christopher Nolan"),
        listOf("Crime", "Thriller", "Drama", "Music"),
        "djklfhasdklfhkadjsfh",
        "https://m.media-amazon.com/images/M/MV5BZWZhMjhhZmYtOTIzOC00MGYzLWI1OGYtM2ZkN2IxNTI4ZWI3XkEyXkFqcGdeQXVyNDAzNDk0MTQ@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        "/title/tt8108198/",
        "Andhadhun",
        9.2,
        2016
    )

    @Test
    fun currentMovieDetail_displayedInUI() {
        //  Given - movie
        val bundle = DetailMovieFragmentArgs(movie1).toBundle()

        //  When DetailMovieFragment is launched to display movie details
        launchFragmentInContainer<DetailMovieFragment>(bundle, R.style.Theme_Binge)

        //  Then
        onView(withId(R.id.ratingTextView)).check(matches(withText(movie1.rating.toString())))
        onView(withId(R.id.directorsTextView))
            .check(matches(withText(movie1.directors.joinToString())))
        onView(withId(R.id.starringTextView)).check(matches(withText(movie1.actors.joinToString())))
        onView(withId(R.id.genreTextView)).check(matches(withText(movie1.genre.joinToString())))
        onView(withId(R.id.yearTextView)).check(matches(withText(movie1.year.toString())))
        onView(withId(R.id.movieTitleTextView)).check(matches(withText(movie1.movieName)))
        onView(withId(R.id.movieDescTextView)).check(matches(withText(movie1.desc)))

    }

}