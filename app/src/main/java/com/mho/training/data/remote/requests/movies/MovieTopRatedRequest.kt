package com.mho.training.data.remote.requests.movies

import android.net.Uri
import com.mho.training.BuildConfig
import com.mho.training.data.remote.models.ServerMovie
import com.mho.training.data.remote.parsers.MovieListJsonUtils
import com.mho.training.data.remote.requests.BaseMovieRequest

class MovieTopRatedRequest(private val region: String) : BaseMovieRequest() {

    //region Override Methods & Callbacks

    public override fun createBuiltUri(): Uri {
        return Uri.parse(MOVIE_TOP_RATED_URL).buildUpon()
            .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
            .appendQueryParameter(QUERY_PARAMETER_REGION, region)
            .build()
    }

    //endregion

    //region Public Methods

    suspend fun requestMovieList(): List<ServerMovie> = try {
        val jsonMovieListResponse = getResponseFromHttpUrl(buildUrl()!!)
        MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse!!)
    } catch (e: Exception) {
        e.printStackTrace()
        mutableListOf()
    }

    //endregion

    companion object {
        private val MOVIE_TOP_RATED_URL = BASE_MOVIE_DB_URL + "movie/top_rated"
    }
}