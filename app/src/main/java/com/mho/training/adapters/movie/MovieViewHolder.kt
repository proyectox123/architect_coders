package com.mho.training.adapters.movie

import androidx.recyclerview.widget.RecyclerView
import com.mho.training.databinding.ItemMovieBinding
import com.example.android.domain.Movie

class MovieViewHolder(private val dataBinding: ItemMovieBinding,
                      private val listener: (Movie) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(movie: Movie){
        dataBinding.movie = movie
        itemView.setOnClickListener { listener(movie) }
    }

}