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

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        if(inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemMovieBinding.inflate(inflater!!, parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }


    inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root){

        init {
            binding.movieItemConstraintLayout.setOnClickListener {
                movieItemClickListener.onMovieItemClicked(getItem(layoutPosition))
            }
        }

        fun bind(movie: Movies) {
            binding.movie = movie
        }

    }

}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }
}