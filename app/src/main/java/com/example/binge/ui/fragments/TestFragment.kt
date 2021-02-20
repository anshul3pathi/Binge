package com.example.binge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.R
import com.example.binge.adapters.TestAdapter
import com.example.binge.model.MovieRepository

class TestFragment : Fragment() {

    private lateinit var adapter: TestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_test, container, false)

        val movieRepository = MovieRepository()

        val items = movieRepository.getGenres()

        adapter = TestAdapter(items)

        val recyclerView: RecyclerView = root.findViewById(R.id.testRecyclerView)
        recyclerView.adapter = adapter
        return root
    }

}