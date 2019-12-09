package com.mho.training.features.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.features.main.MainViewModel.MainUiModel
import com.mho.training.utils.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var viewModel: MainViewModel

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel { MainViewModel(MoviePopularRequest()) }

        movieListAdapter = MovieListAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieListAdapter

        srwMovieList.setOnRefreshListener { viewModel.onMovieListRefresh() }

        viewModel.model.observe(this, Observer(::updateMainUi))
    }

    //endregion

    //region Private Methods

    private fun updateMainUi(model: MainUiModel) = when(model){
        is MainUiModel.Loading -> loadingMovieListInfo()
        is MainUiModel.Error -> showMovieListError()
        is MainUiModel.Content -> showMovieListInfo(model.movies)
        is MainUiModel.Navigation -> openMovieDetails(model.movie)
    }

    //TODO Implementing data binding
    private fun loadingMovieListInfo(){
        srwMovieList.isRefreshing = true
        rvMovieList.visibility = View.GONE
        tvNoDataLabel.visibility = View.GONE
    }

    //TODO Implementing data binding
    private fun showMovieListError(){
        srwMovieList.isRefreshing = false
        rvMovieList.visibility = View.GONE
        tvNoDataLabel.visibility = View.VISIBLE
    }

    //TODO Implementing data binding
    private fun showMovieListInfo(movies: List<MovieEntity>) {
        srwMovieList.isRefreshing = false
        rvMovieList.visibility = View.VISIBLE
        tvNoDataLabel.visibility = View.GONE

        movieListAdapter.movies = movies
    }

    private fun openMovieDetails(movie: MovieEntity){
        Toast.makeText(this, movie.title, Toast.LENGTH_LONG).show()
    }

    //endregion
}
