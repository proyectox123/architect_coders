package com.mho.training.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.Movie
import com.mho.training.data.remote.requests.movies.MovieListTask
import com.mho.training.enums.MovieEnum
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter

    private val MOVIE_GRID_SPAN_VERTICAL_COUNT = 2

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListAdapter =
            MovieListAdapter { movie -> openMovieDetails(movie) }

        rvMovieList.adapter = movieListAdapter

        srwMovieList.setOnRefreshListener {
            onMovieListRefresh()
        }

        onMovieListRefresh()
    }

    //endregion

    //region Private Methods

    private fun openMovieDetails(movie: MovieEntity){
        Toast.makeText(this, movie.title, Toast.LENGTH_LONG).show()
    }

    //endregion

    private fun onMovieListRefresh(){
        MovieListTask(object: MovieListTask.OnMovieListTaskListener{
            override fun updateMovieList(sortBy: MovieEnum, movieList: List<MovieEntity>) {
                if (movieList.isNotEmpty()) {
                    srwMovieList.isRefreshing = false
                    rvMovieList.visibility = View.VISIBLE
                    movieListAdapter.movies = movieList
                } else {
                    srwMovieList.isRefreshing = false
                }
            }

            override fun showErrorTask() {

            }

        }).execute(MovieEnum.POPULAR)
    }
}
