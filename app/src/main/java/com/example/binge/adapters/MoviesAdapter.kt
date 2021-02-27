package com.example.binge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.binge.R
import com.example.binge.databinding.ItemMovieBinding
import com.example.binge.model.data.Movies

class MoviesAdapter(private val movieItemClickListener: MovieItemClicked) :
    ListAdapter<Movies, MoviesAdapter.MoviesViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater)
        return MoviesViewHolder(binding)

//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_movie, parent, false)
//        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root){

//        private val thumbNailImageView: ImageView = itemView.findViewById(R.id.movieThumbnailImageView)
//        private val movieNameTextView: TextView = itemView.findViewById(R.id.movieNameTextView)
//
//        fun bind(movie: Movies) {
//            Glide.with(thumbNailImageView.context)
//                .load(movie.thumbNailUrl)
//                .into(thumbNailImageView)
//            movieNameTextView.text = movie.movieName
//        }

        init {
            binding.mcvMovieThumbnail.setOnClickListener {
                movieItemClickListener.onMovieItemClicked(getItem(layoutPosition))
            }
        }

        fun bind(movie: Movies) {
            binding.movie = movie
//            Glide.with(binding.movieThumbnailImageView.context)
//                .load(movie.thumbNailUrl)
//                .into(binding.movieThumbnailImageView)
        }

    }

}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.movieName == newItem.movieName
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }
}