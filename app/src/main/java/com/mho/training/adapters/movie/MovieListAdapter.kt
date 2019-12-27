package com.mho.training.adapters.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.domain.Movie
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class MovieListAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(parent.bindingInflate(R.layout.item_movie, false), listener)

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}