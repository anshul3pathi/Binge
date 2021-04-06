package com.example.binge.ui.fragments.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.*
import com.example.binge.adapters.FeedAdapter
import com.example.binge.adapters.MovieItemClicked
import com.example.binge.databinding.FragmentMovieFeedBinding
import com.example.binge.model.data.Movies
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovieFeedFragment : Fragment(), MovieItemClicked {

    private val logTag = "MovieFeedFragment"

    @Inject
    lateinit var viewModelFactory: MovieFeedViewModelFactory
    private val viewModel by viewModels<MovieFeedViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMovieFeedBinding

    private var currentTheme = CurrentTheme.LIGHT

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d(logTag, "onCreateView called")
        // Inflate the layout for this fragment
        binding = FragmentMovieFeedBinding.inflate(inflater)

        currentTheme = viewModel.getCurrentTheme()

        feedAdapter = FeedAdapter(this).apply {
            setHasStableIds(true)
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        binding.movieFeedRecyclerView.adapter = feedAdapter

        viewModel.feedData.observe(viewLifecycleOwner, {
            feedAdapter.submitList(it)
        })

        viewModel.sortByToast.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.dataFetchingStatus.observe(viewLifecycleOwner, {
            when (it) {
                DataFetchingStatus.LOADING -> {
                    binding.dataFetchingProgressBar.visibility = View.VISIBLE

                    binding.fetchingDataTextView.visibility = View.VISIBLE
                }
                DataFetchingStatus.DONE -> {
                    binding.dataFetchingProgressBar.visibility = View.GONE
                    binding.fetchingDataTextView.visibility = View.GONE
                }
                else -> {
                    Toast.makeText(context, "Error loading data!", Toast.LENGTH_LONG).show()
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BingeApplication).appComponent.inject(this)
    }

    override fun onMovieItemClicked(movie: Movies) {
        val action = MovieFeedFragmentDirections.actionDetailFragment(movie)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionChangeTheme -> {
            changeCurrentTheme()
            true
        }
        R.id.sortByAlpha -> {
            viewModel.changeSortByType(SortBy.ALPHABETS)
            true
        }
        R.id.sortByRating -> {
            viewModel.changeSortByType(SortBy.RATING)
            true
        }
        R.id.sortByYear -> {
            viewModel.changeSortByType(SortBy.YEAR)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun changeCurrentTheme() {
        currentTheme = if (currentTheme == CurrentTheme.LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.saveThemePreference(CurrentTheme.DARK)
            CurrentTheme.DARK
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.saveThemePreference(CurrentTheme.LIGHT)
            CurrentTheme.LIGHT
        }
    }

}