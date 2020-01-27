package com.mho.training.adapters.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Movie
import com.example.android.domain.MovieCarousel
import com.mho.training.R
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class MovieCarouselAdapter(
    private val listener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieCarouselViewHolder>() {

    //region Fields

    var movieCarouselList: List<MovieCarousel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.name == new.name }
    )

    //endregion

    //region Override Methods & Callbacks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieCarouselViewHolder(parent.bindingInflate(R.layout.item_carousel_movie, false), listener)

    override fun getItemCount(): Int = movieCarouselList.size

    override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
        holder.bind(movieCarouselList[position])
    }

    //endregion

}