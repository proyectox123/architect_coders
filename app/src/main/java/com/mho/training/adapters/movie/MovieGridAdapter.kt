package com.mho.training.adapters.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.coreandroid.extensions.basicDiffUtil
import com.mho.training.coreandroid.extensions.bindingInflate

class MovieGridAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieGridViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieGridViewHolder(parent.bindingInflate(R.layout.item_grid_movie, false), listener)

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holderGrid: MovieGridViewHolder, position: Int) {
        holderGrid.bind(movies[position])
    }
}