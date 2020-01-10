package com.mho.training.adapters.movie

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.mho.training.databinding.ItemGridMovieBinding

class MovieGridViewHolder(private val dataBinding: ItemGridMovieBinding,
                          private val listener: (Movie) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(movie: Movie){
        dataBinding.movie = movie
        itemView.setOnClickListener { listener(movie) }
    }

}