package com.mho.training.data.remote.requests.trailers

import android.net.Uri
import com.mho.training.BuildConfig.MOVIE_DB_API_KEY
import com.mho.training.data.remote.models.ServerTrailer
import com.mho.training.data.remote.parsers.TrailerListJsonUtils
import com.mho.training.data.remote.requests.BaseMovieRequest

class TrailerRequest(private val movieId: Int) : BaseMovieRequest() {

    //region Override Methods & Callbacks

    public override fun createBuiltUri(): Uri {
        val movieTrailerUrl = MOVIE_TRAILER_URL + movieId + MOVIE_TRAILER_VIDEO_PATH

        return Uri.parse(movieTrailerUrl)
            .buildUpon()
            .appendQueryParameter(QUERY_PARAMETER_API_KEY, MOVIE_DB_API_KEY)
            .build()
    }

    //endregion

    //region Public Methods

    suspend fun requestTrailerList(): List<ServerTrailer> = try {
        val jsonMovieListResponse = getResponseFromHttpUrl(buildUrl()!!)
        TrailerListJsonUtils.getTrailerListFromJson(jsonMovieListResponse!!)
    } catch (e: Exception) {
        e.printStackTrace()
        mutableListOf()
    }

    //endregion

    companion object {

        private val MOVIE_TRAILER_URL = BASE_MOVIE_DB_URL + "movie/"
        private val MOVIE_TRAILER_VIDEO_PATH = "/videos"
    }
}