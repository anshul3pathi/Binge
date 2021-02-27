package com.example.binge.ui.fragments.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.binge.R
import com.example.binge.adapters.FeedAdapter
import com.example.binge.adapters.MovieItemClicked
import com.example.binge.databinding.FragmentMovieFeedBinding
import com.example.binge.model.DataFetchingStatus
import com.example.binge.model.SortBy
import com.example.binge.model.data.Movies

class MovieFeedFragment : Fragment(), MovieItemClicked {

    private lateinit var viewModel: MovieFeedViewModel
    private lateinit var binding: FragmentMovieFeedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieFeedBinding.inflate(inflater)

        viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MovieFeedViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val feedAdapter = FeedAdapter(this)

        binding.movieFeedRecyclerView.adapter = feedAdapter

        viewModel.sortBy.observe(this, { sort ->
            when (sort) {
                SortBy.ALPHABETS -> {
                    viewModel.feedLiveDataAlpha.observe(this, {
                        feedAdapter.submitList(it)
                    })
                }
                SortBy.RATING -> {
                    viewModel.feedLiveDataRating.observe(this, {
                        feedAdapter.submitList(it)
                    })
                }
                else -> {
                    viewModel.feedLiveDataYear.observe(this, {
                        feedAdapter.submitList(it)
                    })
                }
            }
        })

        viewModel.dataFetchingStatus.observe(this, {
            if (it == DataFetchingStatus.LOADING) {
                binding.dataFetchingProgressBar.visibility = View.VISIBLE
                binding.fetchingDataTextView.visibility = View.VISIBLE
            } else if(it == DataFetchingStatus.DONE) {
                binding.dataFetchingProgressBar.visibility = View.GONE
                binding.fetchingDataTextView.visibility = View.GONE
            } else {
                Toast.makeText(context, "Error loading data!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.darkModeStatus.observe(viewLifecycleOwner, {
            if(it!!) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    override fun onMovieItemClicked(movie: Movies) {
//        Log.d("Feed Fragment", "Name of movie clicked - ${movie.movieName}")
        val action = MovieFeedFragmentDirections.actionDetailFragment()
        action.currentMovie = movie
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.actionSortBy -> {
            when (viewModel.sortBy.value) {
                SortBy.ALPHABETS -> {
                    viewModel.changeSortByType(SortBy.RATING)
                    Toast.makeText(context, "Sorting by rating.", Toast.LENGTH_SHORT).show()
                }
                SortBy.RATING -> {
                    viewModel.changeSortByType(SortBy.YEAR)
                    Toast.makeText(context, "Sorting by year.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.changeSortByType(SortBy.ALPHABETS)
                    Toast.makeText(context, "Sorting alphabetically.", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        R.id.actionChangeTheme -> {
            if(viewModel.darkModeStatus.value!!) {
                Toast.makeText(context, "Switched to Light Mode.", Toast.LENGTH_SHORT).show()
                viewModel.changeDarkModeStatus(true)
            } else {
                Toast.makeText(context, "Switched to Dark Mode", Toast.LENGTH_SHORT).show()
                viewModel.changeDarkModeStatus(false)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}