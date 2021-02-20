package com.example.binge

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.model.data.Movies

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val items = ArrayList<Movies>()

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.findViewById(R.id.movieNameTextView)
        val actors: TextView = itemView.findViewById(R.id.actorsTextView)
        val rating: TextView = itemView.findViewById(R.id.ratingTextView)
        val year: TextView = itemView.findViewById(R.id.yearTextView)
        val desc: TextView = itemView.findViewById(R.id.descTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = items[position]
        holder.actors.text = stringArrayToString(currentItem.actors)
        holder.desc.text = currentItem.desc
        holder.movieName.text = currentItem.movieName
        holder.rating.text = currentItem.rating.toString()
        holder.year.text = currentItem.year.toString()
    }

    fun updateData(data: List<Movies>) {
        Log.d("MoviesAdapter", "Size - ${data.size}")
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

// Util
private fun stringArrayToString(actors: List<String>): String {
    var actorsString = ""
    for (item in actors) {
        actorsString = "$actorsString, $item"
    }
    return actorsString
}