package com.mho.training.adapters.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class MovieCarouselItemListAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieListViewHolder(parent.bindingInflate(R.layout.item_carousel_item_movie, false), listener)

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holderGrid: MovieListViewHolder, position: Int) {
        holderGrid.bind(movies[position])
    }
}