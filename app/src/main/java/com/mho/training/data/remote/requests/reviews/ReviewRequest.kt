package com.mho.training.data.remote.requests.reviews

import android.net.Uri
import com.mho.training.BuildConfig.MOVIE_DB_API_KEY
import com.mho.training.data.remote.models.ServerReview
import com.mho.training.data.remote.parsers.ReviewListJsonUtils
import com.mho.training.data.remote.requests.BaseMovieRequest

class ReviewRequest(private val movieId: Int) : BaseMovieRequest() {

    //region Override Methods & Callbacks

    public override fun createBuiltUri(): Uri {
        val movieTrailerUrl = MOVIE_REVIEW_URL + movieId + MOVIE_REVIEW_PATH

        return Uri.parse(movieTrailerUrl)
            .buildUpon()
            .appendQueryParameter(QUERY_PARAMETER_API_KEY, MOVIE_DB_API_KEY)
            .build()
    }

    //endregion

    //region Public Methods

    suspend fun requestReviewList(): List<ServerReview>{
        try {
            val jsonReviewListResponse = getResponseFromHttpUrl(buildUrl()!!)
            return ReviewListJsonUtils.getReviewListFromJson(jsonReviewListResponse!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    //endregion

    companion object {

        private val MOVIE_REVIEW_URL = BASE_MOVIE_DB_URL + "movie/"
        private val MOVIE_REVIEW_PATH = "/reviews"
    }
}