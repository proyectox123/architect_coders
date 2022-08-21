@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.domain.Credit
import com.example.android.domain.Keyword
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mho.training.R
import com.mho.training.adapters.credit.CreditListAdapter
import com.mho.training.adapters.keyword.KeywordListAdapter
import com.mho.training.adapters.movie.MovieGridAdapter
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.features.trailer.mvi.adapter.TrailerListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@BindingAdapter("itemsGrid")
fun RecyclerView.setMovieItemsToGrid(movies: List<Movie>?) {
    (adapter as? MovieGridAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}

@BindingAdapter("itemsList")
fun RecyclerView.setMovieItemsToList(movies: List<Movie>?) {
    (adapter as? MovieListAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}

@BindingAdapter("items")
fun RecyclerView.setCredits(credits: List<Credit>?) {
    (adapter as? CreditListAdapter)?.let {
        it.credits = credits ?: emptyList()
    }
}

@BindingAdapter("items")
fun RecyclerView.setKeywords(keywords: List<Keyword>?) {
    (adapter as? KeywordListAdapter)?.let {
        it.keywords = keywords ?: emptyList()
    }
}

@BindingAdapter("items")
fun RecyclerView.setReviews(reviews: List<Review>?) {
    (adapter as? ReviewListAdapter)?.let {
        it.reviews = reviews ?: emptyList()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@BindingAdapter("items")
fun RecyclerView.setTrailers(trailers: List<Trailer>?) {
    (adapter as? TrailerListAdapter)?.let {
        it.trailers = trailers ?: emptyList()
    }
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.setRefreshing(canRefreshing: Boolean?){
    isRefreshing = canRefreshing ?: false
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let { if (visible) View.VISIBLE else View.GONE } ?: View.GONE
}

@BindingAdapter("isFavorite")
fun FloatingActionButton.setFavoriteIcon(isFavorite: Boolean?){
    setImageResource(
        isFavorite?.let { if(isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_border } ?: R.drawable.ic_favorite_border
    )
}