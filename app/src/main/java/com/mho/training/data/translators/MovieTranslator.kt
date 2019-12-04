package com.mho.training.data.translators

import com.mho.training.data.database.converters.DateConverter
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.Movie

import java.util.ArrayList

object MovieTranslator {

    fun translateMovieList(movieList: List<Movie>?): List<MovieEntity>? {
        if (movieList.isNullOrEmpty()) {
            return null
        }

        return ArrayList<MovieEntity>().apply {
            for (movie in movieList) {
                add(translateMovie(movie))
            }
        }
    }

    private fun translateMovie(movie: Movie) = MovieEntity(
        movie.id,
        movie.title,
        DateConverter.toDate(movie.releaseDate)!!,
        movie.posterPath,
        movie.voteAverage,
        movie.plotSynopsis
    )
}