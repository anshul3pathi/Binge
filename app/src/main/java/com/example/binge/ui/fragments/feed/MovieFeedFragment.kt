package com.example.binge.ui.fragments.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.binge.adapters.FeedAdapter
import com.example.binge.databinding.FragmentMovieFeedBinding
import com.example.binge.model.DataFetchingStatus

class MovieFeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMovieFeedBinding.inflate(inflater)

        val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MovieFeedViewModel::class.java)

        val feedAdapter = FeedAdapter()

        binding.movieFeedRecyclerView.adapter = feedAdapter

        viewModel.feedLiveData.observe(viewLifecycleOwner, {
            feedAdapter.submitList(it)
        })

        viewModel.dataFetchingStatus.observe(viewLifecycleOwner, {
            Log.d("MovieFeedFragment", "Data Fetching Status Changed!")
            if (it == DataFetchingStatus.LOADING) {
                binding.dataFetchingProgressBar.visibility = View.VISIBLE
                binding.fetchingDataTextView.visibility = View.VISIBLE
            } else if(it == DataFetchingStatus.DONE) {
                binding.dataFetchingProgressBar.visibility = View.GONE
                binding.fetchingDataTextView.visibility = View.GONE
            }
        })

        return binding.root
    }
}