package com.example.binge.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.binge.R
import com.example.binge.model.data.Movies
import com.example.binge.model.repository.MoviesRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@ExperimentalTime
class MovieFeedFragmentTest {

    @Inject
    lateinit var repository: MoviesRepo

    private val movie = Movies(
        listOf("Ayushman", "Tabbu", "Radhika"),
        "Kafi sexy movie",
        listOf("Dont know", "Christopher Nolan"),
        listOf("Crime"),
        "djklfhasdklfhkadjsfh",
        "https://m.media-amazon.com/images/M/MV5BZWZhMjhhZmYtOTIzOC00MGYzLWI1OGYtM2ZkN2IxNTI4ZWI3XkEyXkFqcGdeQXVyNDAzNDk0MTQ@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        "adlskfjhaklsdfhadklsf",
        "Andhadhun",
        9.2,
        2016
    )

    @Test
    fun clickMovie_navigateToDetailMovieFragment() {
        //  Given - On the movie feed screen
        val scenario = launchFragmentInContainer<MovieFeedFragment>(Bundle(), R.style.Theme_Binge)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        //  When - Click on the first movie item
        onView(withId(R.id.movieRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        //  Then - Verify that we navigate to the detail movie screen
        verify(navController).navigate(
            MovieFeedFragmentDirections.actionDetailFragment(movie)
        )
    }

}