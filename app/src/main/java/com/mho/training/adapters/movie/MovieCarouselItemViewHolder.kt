package com.mho.training.adapters.movie

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.mho.training.databinding.ItemCarouselItemMovieBinding

class MovieCarouselItemViewHolder(
    private val dataBinding: ItemCarouselItemMovieBinding,
    private val listener: (Movie) -> Unit
) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(movie: Movie){
        dataBinding.movie = movie
        itemView.setOnClickListener { listener(movie) }
    }

}