package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.Review
import com.example.android.framework.data.remote.requests.Result
import com.example.android.framework.data.remote.requests.review.ReviewRequest
import com.example.android.framework.data.remote.requests.safeApiCall
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ReviewDataSource(private val resources: Resources) : RemoteReviewDataSource {

    override suspend fun getReviewList(movieId: Int): Result<List<Review>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestReviewList(movieId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_reviews)
        )
    }

    private suspend fun requestReviewList(movieId: Int): Result<List<Review>> {
        val response = ReviewRequest.service
            .getReviewListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return Result.Success(results.map { it.toDomainReview() })
            }
        }

        return Result.Error(IOException(resources.getString(R.string.error_during_fetching_reviews)))
    }
}