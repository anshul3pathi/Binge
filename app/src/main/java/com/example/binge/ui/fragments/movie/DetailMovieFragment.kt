package com.example.binge.ui.fragments.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.binge.BingeApplication
import com.example.binge.R
import com.example.binge.databinding.FragmentDetailMovieBinding
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import com.example.binge.ui.activities.main.MainActivity
import com.example.binge.ui.fragments.feed.MovieFeedViewModel
import kotlin.time.ExperimentalTime

class DetailMovieFragment : Fragment() {

    companion object {
        const val IMDB_DOT_COM = "https://imdb.com"
    }

    private lateinit var currentMovie: Movies


    @ExperimentalTime
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDetailMovieBinding.inflate(inflater)

        val viewModel by viewModels<DetailMovieViewModel>()

        binding.viewModel = viewModel

//        viewModel.currentMovie.observe(viewLifecycleOwner, {
//            binding.movie = (it as Result.Success).data
//        })

        arguments?.let {
            val safeArgs = DetailMovieFragmentArgs.fromBundle(it)
            binding.movie = safeArgs.currentMovie!!
        }

        viewModel.openImdbLiveData.observe(viewLifecycleOwner, {
            if(it) {
                openImdbUrl(binding.movie!!.imdbUrl)
                viewModel.imdbUrlOpened()
            }
        })

        return binding.root
    }

    private fun openImdbUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse("$IMDB_DOT_COM$url"))
    }

}