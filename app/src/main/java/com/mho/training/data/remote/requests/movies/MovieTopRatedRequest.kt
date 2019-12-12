package com.mho.training.data.remote.requests.movies

import android.net.Uri

import com.mho.training.BuildConfig
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.parsers.MovieListJsonUtils
import com.mho.training.data.remote.requests.BaseMovieRequest
import com.mho.training.data.translators.MovieTranslator

class MovieTopRatedRequest : BaseMovieRequest() {

    //region Override Methods & Callbacks

    public override fun createBuiltUri(): Uri {
        return Uri.parse(MOVIE_TOP_RATED_URL).buildUpon()
            .appendQueryParameter(
                QUERY_PARAMETER_API_KEY,
                BuildConfig.MOVIE_DB_API_KEY
            )
            .build()
    }

    //endregion

    //region Public Methods

    suspend fun requestMovieList(): List<MovieEntity>?{
        try {
            val jsonMovieListResponse = getResponseFromHttpUrl(buildUrl()!!)

            return MovieTranslator.translateMovieList(MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    //endregion

    companion object {
        private val MOVIE_TOP_RATED_URL = BASE_MOVIE_DB_URL + "movie/top_rated"
    }
}