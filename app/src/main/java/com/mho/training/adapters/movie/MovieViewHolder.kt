package com.mho.training.adapters.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.Movie
import com.mho.training.utils.loadUrl
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(movie: MovieEntity) {
        itemView.ivMoviePoster.loadUrl(movie.posterPath, R.drawable.ic_movie_black_48dp)
    }
}