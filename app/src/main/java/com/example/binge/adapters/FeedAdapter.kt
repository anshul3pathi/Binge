package com.example.binge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.databinding.ItemFeedBinding
import com.example.binge.model.data.GenreFeed
import com.example.binge.model.data.Movies
import kotlinx.coroutines.GlobalScope

class FeedAdapter(private val movieItemClickListener: MovieItemClicked) :
            ListAdapter<GenreFeed, FeedAdapter.FeedItemViewHolder>(FeedDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        if(inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemFeedBinding.inflate(inflater!!, parent, false)
        return FeedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }


    inner class FeedItemViewHolder(private val binding: ItemFeedBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val moviesAdapter by lazy {
            val adapter = MoviesAdapter(movieItemClickListener).apply {
                setHasStableIds(true)
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            binding.movieRecyclerView.adapter = adapter
            adapter
        }

        fun bind(genreFeed: GenreFeed) {
            binding.feed = genreFeed
            moviesAdapter.submitList(genreFeed.movies)
        }
    }
}

class FeedDiffCallback : DiffUtil.ItemCallback<GenreFeed>() {
    override fun areItemsTheSame(oldItem: GenreFeed, newItem: GenreFeed): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreFeed, newItem: GenreFeed): Boolean {
        return oldItem == newItem
    }
}

interface MovieItemClicked {
    fun onMovieItemClicked(movie: Movies)
}