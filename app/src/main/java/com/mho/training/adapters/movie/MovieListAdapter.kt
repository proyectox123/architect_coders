package com.mho.training.adapters.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class MovieListAdapter(private val listener: (MovieEntity) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var movies: List<MovieEntity> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(parent.bindingInflate(R.layout.item_movie, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener { listener(movie) }
    }
}