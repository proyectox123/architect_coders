package com.mho.training.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.utils.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<MovieEntity>?) {
    (adapter as? MovieListAdapter)?.let {
        it.movies = movies ?: emptyList()
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