package com.mho.training.data.remote.requests.movies

import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.parsers.MovieListJsonUtils
import com.mho.training.data.translators.MovieTranslator

class MovieListTask() {

    suspend fun requestPopularMovieList(): List<MovieEntity>?{
        val moviePopular = MoviePopularRequest()
        val movieListRequestUrl = moviePopular.buildUrl()
        try {
            val jsonMovieListResponse = moviePopular.getResponseFromHttpUrl(movieListRequestUrl!!)

            return MovieTranslator.translateMovieList(MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}