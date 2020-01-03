package com.mho.training.features.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.data.repositories.MovieRepository
import com.example.android.data.repositories.RegionRepository
import com.example.android.domain.Movie
import com.example.android.usecases.GetFavoriteMovieListUseCase
import com.example.android.usecases.GetPopularMovieListUseCase
import com.example.android.usecases.GetTopRatedMovieListUseCase
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityMainBinding
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.permissions.PermissionRequester
import com.mho.training.sources.MovieDataSource
import com.mho.training.sources.PlayServicesLocationDataSource
import com.mho.training.sources.RoomDataSource
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var viewModel: MainViewModel

    private val coarsePermissionRequester = PermissionRequester(this)

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MainViewModel(
                GetFavoriteMovieListUseCase(getMovieRepository()),
                GetPopularMovieListUseCase(getMovieRepository()),
                GetTopRatedMovieListUseCase(getMovieRepository())
            )
        }

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainActivity
        }

        movieListAdapter = MovieListAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieListAdapter

        srwMovieList.setOnRefreshListener { viewModel.onMovieListRefresh() }

        viewModel.navigateToMovie.observe(this, EventObserver(::openMovieDetails))
        viewModel.movieCategory.observe(this, Observer(::updateMovieCategory))
        viewModel.requestLocationPermission.observe(this, EventObserver { checkLocationPermission() })
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
            else -> R.id.menu_movie_highest_rated
        }

        menu.findItem(menuItemId)?.isChecked = true

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
            R.id.menu_movie_most_popular -> onOptionsMovieMostPopularSelected()
            R.id.menu_movie_highest_rated -> onOptionsMovieHighestRatedSelected()
            R.id.menu_movie_favorites -> onOptionsMovieFavoritesSelected()
            else -> super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    private fun getMovieRepository() = MovieRepository(
        RoomDataSource(
            app.db,
            resources
        ), MovieDataSource(
            resources
        ), RegionRepository(
            PlayServicesLocationDataSource(app),
            AndroidPermissionChecker(app)
        )
    )

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

    private fun updateMovieCategory(movieCategory: MovieCategoryEnum){
        Log.d(TAG, "updateMovieCategory movieCategory -> $movieCategory")
        viewModel.onMovieListRefresh()
    }

    private fun openMovieDetails(movie: Movie){
        startActivity<MovieDetailActivity> {
            putExtra(Constants.EXTRA_MOVIE, movie.toParcelableMovie())
        }
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
