package com.mho.training.adapters.movie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.models.Movie
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.inflate
import com.mho.training.utils.loadUrl
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = parent.inflate(R.layout.item_movie, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }
}