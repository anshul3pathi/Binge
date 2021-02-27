package com.example.binge.ui.fragments.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import com.example.binge.R
import com.example.binge.databinding.FragmentDetailMovieBinding
import com.example.binge.ui.activities.main.MainActivity
import com.example.binge.ui.fragments.feed.MovieFeedViewModel

class DetailMovieFragment : Fragment() {

    companion object {
        const val IMDB_DOT_COM = "https://imdb.com"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailMovieBinding.inflate(inflater)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(DetailMovieViewModel::class.java)

        binding.viewModel = viewModel

        arguments?.let {
            val safeArgs = DetailMovieFragmentArgs.fromBundle(it)
            binding.movie = safeArgs.currentMovie!!
            (requireActivity() as MainActivity).supportActionBar?.title =
                safeArgs.currentMovie!!.movieName
        }

        viewModel.openImdbLiveData.observe(viewLifecycleOwner, {
            if(it) {
                openImdbUrl(binding.movie!!.imdbUrl)
                viewModel.imdbUrlOpened()
            }
        })

        return binding.root
    }
//
    private fun openImdbUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context!!, Uri.parse("$IMDB_DOT_COM$url"))
    }

}