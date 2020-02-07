package com.mho.training.sources

import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.Review
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.frameworkretrofit.data.requests.review.ReviewRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ReviewServerDataSource(
    private val errorUnableToFetchReviews: String,
    private val errorDuringFetchingReviews: String
) : RemoteReviewDataSource {

    override suspend fun getReviewList(movieId: Int): DataResult<List<Review>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestReviewList(movieId) },
            errorMessage = errorUnableToFetchReviews
        )
    }

    private suspend fun requestReviewList(movieId: Int): DataResult<List<Review>> {
        val response = ReviewRequest.service
            .getReviewListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainReview() })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingReviews))
    }
}