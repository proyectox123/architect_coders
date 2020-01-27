package com.mho.training.features.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.MovieCarousel
import com.mho.training.R
import com.mho.training.adapters.movie.MovieCarouselAdapter
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityMainBinding
import com.mho.training.features.main.MainViewModel.Navigation
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.permissions.PermissionRequester
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieCarouselAdapter: MovieCarouselAdapter
    private lateinit var component: MainActivityComponent

    private val viewModel: MainViewModel by lazy {
        getViewModel { component.mainViewModel }
    }

    private val coarsePermissionRequester = PermissionRequester(this)

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(MainActivityModule())

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainActivity
        }

        movieCarouselAdapter = MovieCarouselAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieCarouselAdapter

        srwMovieList.setOnRefreshListener { viewModel.onMovieListRefresh() }

        viewModel.favoriteMovies.observe(this, Observer(::validateFavoriteMovies))
        viewModel.events.observe(this, EventObserver(::validateEvents))

        viewModel.onMovieListRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie, menu)

        return true
    }

    //endregion

    //region Private Methods

    private fun validateFavoriteMovies(listMovie: List<Movie>){
        val favoriteCarouselIndex = movieCarouselAdapter.movieCarouselList
            .indices
            .firstOrNull { movieCarouselAdapter.movieCarouselList[it].name == "Favorites" } ?: -1

        if (favoriteCarouselIndex != -1) {
            movieCarouselAdapter.movieCarouselList =
                movieCarouselAdapter.movieCarouselList.toMutableList().apply {
                    this[favoriteCarouselIndex] = MovieCarousel(this[favoriteCarouselIndex].name, listMovie)
                }
        }
    }

    private fun validateEvents(navigation: Navigation){
        when(navigation){
            is Navigation.NavigateToMovie -> navigation.run { openMovieDetails(movie) }
            Navigation.RequestLocationPermission -> checkLocationPermission()
        }
    }

    private fun openMovieDetails(movie: Movie){
        Log.d(TAG, "openMovieDetails movie -> $movie")
        startActivity<MovieDetailActivity> {
            putExtra(Constants.EXTRA_MOVIE, movie.toParcelableMovie())
        }
        overridePendingTransition(R.anim.anim_entry, R.anim.anim_exit)
    }

    private fun checkLocationPermission() {
        coarsePermissionRequester.requestAccessCoarseLocation { hasPermission ->
            viewModel.onCoarsePermissionRequested(hasPermission)
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = MainActivity::class.java.simpleName

    }

    //endregion
}
