package com.mho.training.features.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.adapters.movie.MovieGridAdapter
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityMainBinding
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.features.main.MainViewModel.Navigation
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.permissions.PermissionRequester
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieGridAdapter: MovieGridAdapter
    //private lateinit var component: MainActivityComponent

    //private val viewModel: MainViewModel by lazy {
    //    getViewModel { component.mainViewModel }
    //}

    private val viewModel: MainViewModel by currentScope.viewModel(this)

    private val coarsePermissionRequester = PermissionRequester(this)

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //component = app.component.plus(MainActivityModule())

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainActivity
        }

        movieGridAdapter = MovieGridAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieGridAdapter

        srwMovieList.setOnRefreshListener { viewModel.onMovieListRefresh() }

        viewModel.movieCategory.observe(this, Observer(::updateMovieCategory))
        viewModel.favoriteMovies.observe(this, Observer { viewModel.onMovieFavoriteListUpdate(it) })
        viewModel.events.observe(this, Observer{ event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie, menu)

        return true
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        val menuItemId = when(viewModel.onMovieCategory()){
            MovieCategoryEnum.FAVORITE -> R.id.menu_movie_favorites
            MovieCategoryEnum.POPULAR -> R.id.menu_movie_most_popular
            MovieCategoryEnum.IN_THEATERS -> R.id.menu_movie_in_theaters
            MovieCategoryEnum.TOP_RATED -> R.id.menu_movie_highest_rated
            else -> -1
        }

        menu.findItem(menuItemId)?.isChecked = true

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
            R.id.menu_movie_most_popular -> onOptionsMovieMostPopularSelected()
            R.id.menu_movie_highest_rated -> onOptionsMovieHighestRatedSelected()
            R.id.menu_movie_favorites -> onOptionsMovieFavoritesSelected()
            R.id.menu_movie_in_theaters -> onOptionsMovieInTheatersSelected()
            else -> super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    private fun onOptionsMovieMostPopularSelected(): Boolean {
        viewModel.onMoviePopularListRefresh()
        return true
    }

    private fun onOptionsMovieHighestRatedSelected(): Boolean {
        viewModel.onMovieHighestRatedListRefresh()
        return true
    }

    private fun onOptionsMovieFavoritesSelected(): Boolean {
        viewModel.onMovieFavoriteListRefresh()
        return true
    }

    private fun onOptionsMovieInTheatersSelected(): Boolean {
        viewModel.onMovieInTheatersListRefresh()
        return true
    }

    private fun updateMovieCategory(movieCategory: MovieCategoryEnum){
        logD("updateMovieCategory movieCategory -> $movieCategory")
        viewModel.onMovieListRefresh()
    }

    private fun validateEvents(navigation: Navigation){
        when(navigation){
            is Navigation.NavigateToMovie -> navigation.run { openMovieDetails(movie) }
            Navigation.RequestLocationPermission -> checkLocationPermission()
        }
    }

    private fun openMovieDetails(movie: Movie){
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
