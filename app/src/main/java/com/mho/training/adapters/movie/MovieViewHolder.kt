package com.mho.training.adapters.movie

import androidx.recyclerview.widget.RecyclerView
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.databinding.ItemMovieBinding

class MovieViewHolder(private val dataBinding: ItemMovieBinding,
                      private val listener: (MovieEntity) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(movie: MovieEntity){
        dataBinding.movie = movie
        itemView.setOnClickListener { listener(movie) }
    }

}