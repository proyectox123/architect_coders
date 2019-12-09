package com.mho.training.features.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.database.tables.MovieEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainNavigator {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var viewModel: MainViewModel

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()
        viewModel.setNavigator(this)

        movieListAdapter = MovieListAdapter { movie -> openMovieDetails(movie) }

        rvMovieList.adapter = movieListAdapter

        srwMovieList.setOnRefreshListener {
            viewModel.onMovieListRefresh()
        }

        viewModel.onMovieListRefresh()
    }

    override fun showUpdateMovieListError() {
        srwMovieList.isRefreshing = false
        rvMovieList.visibility = View.GONE
    }

    override fun updateMovieList(movieList: List<MovieEntity>) {
        srwMovieList.isRefreshing = false
        rvMovieList.visibility = View.VISIBLE
        movieListAdapter.movies = movieList
    }

    //endregion

    //region Private Methods

    private fun openMovieDetails(movie: MovieEntity){
        Toast.makeText(this, movie.title, Toast.LENGTH_LONG).show()
    }

    //endregion
}
