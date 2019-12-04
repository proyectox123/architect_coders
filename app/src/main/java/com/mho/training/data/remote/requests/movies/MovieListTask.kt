package com.mho.training.data.remote.requests.movies

import android.os.AsyncTask
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.Movie
import com.mho.training.data.remote.parsers.MovieListJsonUtils
import com.mho.training.data.translators.MovieTranslator
import com.mho.training.enums.MovieEnum

class MovieListTask(private val onMovieListTaskListener: OnMovieListTaskListener) :
    AsyncTask<MovieEnum, Void, List<MovieEntity>>() {

    //region Fields

    private var sortBy = MovieEnum.POPULAR

    private val moviePopularList: List<Movie>?
        get() {
            val moviePopular = MoviePopularRequest()
            val movieListRequestUrl = moviePopular.buildUrl()
            try {
                val jsonMovieListResponse =
                    moviePopular.getResponseFromHttpUrl(movieListRequestUrl!!)

                return MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    private val movieTopRatedList: List<Movie>?
        get() {
            val moviePopular = MovieTopRatedRequest()
            val movieListRequestUrl = moviePopular.buildUrl()
            try {
                val jsonMovieListResponse =
                    moviePopular.getResponseFromHttpUrl(movieListRequestUrl!!)

                return MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    //endregion

    //region Override Methods & Callbacks

    override fun doInBackground(vararg params: MovieEnum): List<MovieEntity>? {
        if (params.size > 0) {
            sortBy = params[0]
            return MovieTranslator.translateMovieList(getMovieList(sortBy!!))
        }

        return null
    }

    override fun onPostExecute(movieList: List<MovieEntity>?) {
        super.onPostExecute(movieList)
        if (movieList.isNullOrEmpty()) {
            onMovieListTaskListener.showErrorTask()
            return
        }

        onMovieListTaskListener.updateMovieList(sortBy, movieList)
    }

    //endregion

    //region Private Methods

    private fun getMovieList(sortBy: MovieEnum): List<Movie>? {
        when (sortBy) {
            MovieEnum.POPULAR -> return moviePopularList
            MovieEnum.TOP_RATED -> return movieTopRatedList
            else -> return null
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    interface OnMovieListTaskListener {
        fun updateMovieList(sortBy: MovieEnum, movieList: List<MovieEntity>)
        fun showErrorTask()
    }

    //endregion
}