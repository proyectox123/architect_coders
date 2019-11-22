package com.mho.training.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mho.training.R
import com.mho.training.features.MovieListAdapter
import com.mho.training.models.Movie
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

        movieListAdapter = MovieListAdapter { movie -> openMovieDetails(movie) }

        rvMovieList.adapter = movieListAdapter

        srwMovieList.setOnRefreshListener {
            //Refresh movie list
        }

        initData()
    }

    //endregion

    //region Private Methods

    private fun initData(){
        val movieList = ArrayList<Movie>()
        movieList.add(Movie(1, "Title 1", "path1"))
        movieList.add(Movie(2, "Title 2", "path2"))
        movieList.add(Movie(3, "Title 3", "path3"))
        movieList.add(Movie(4, "Title 4", "path4"))

        movieListAdapter.movies = movieList
    }

    private fun openMovieDetails(movie: Movie){
        Toast.makeText(this, movie.title, Toast.LENGTH_LONG).show()
    }

    //endregion
}
