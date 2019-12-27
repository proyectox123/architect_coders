package com.mho.training.data.remote.requests.movies

import android.net.Uri
import com.mho.training.BuildConfig
import com.mho.training.data.remote.models.ServerMovie
import com.mho.training.data.remote.parsers.MovieListJsonUtils
import com.mho.training.data.remote.requests.BaseMovieRequest

class MoviePopularRequest : BaseMovieRequest() {

    //region Override Methods & Callbacks

    public override fun createBuiltUri(): Uri {
        return Uri.parse(MOVIE_POPULAR_URL)
            .buildUpon()
            .appendQueryParameter(
                QUERY_PARAMETER_API_KEY,
                BuildConfig.MOVIE_DB_API_KEY
            )
            .build()
    }

    //endregion

    //region Public Methods

    suspend fun requestMovieList(): List<ServerMovie>{
        try {
            val jsonMovieListResponse = getResponseFromHttpUrl(buildUrl()!!)
            return MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    //endregion

    companion object {
        private val MOVIE_POPULAR_URL = BASE_MOVIE_DB_URL + "movie/popular"
    }
}