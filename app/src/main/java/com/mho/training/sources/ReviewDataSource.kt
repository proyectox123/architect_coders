package com.mho.training.sources

import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.Review
import com.example.android.framework.data.remote.requests.RetrofitRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewDataSource : RemoteReviewDataSource {

    override suspend fun getReviewList(movieId: Int): List<Review> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getReviewListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)
            .await()
            .body()
            ?.results
            ?.map { it.toDomainReview() }
            ?: mutableListOf()
    }
}