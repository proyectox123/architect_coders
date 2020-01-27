package com.mho.training.adapters.movie

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.example.android.domain.MovieCarousel
import com.mho.training.databinding.ItemCarouselMovieBinding

class MovieCarouselViewHolder(
    private val dataBinding: ItemCarouselMovieBinding,
    private val listener: (Movie) -> Unit
) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(movieCarousel: MovieCarousel){
        dataBinding.movies = movieCarousel.movies
        dataBinding.tvCarouselTitle.text = movieCarousel.name

        val adapter = MovieCarouselItemListAdapter(listener)
        dataBinding.rvCarouselMovie.adapter = adapter
        adapter.movies = movieCarousel.movies
    }

}