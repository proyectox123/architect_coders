package com.mho.training.sources

import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.Review
import com.mho.training.data.remote.requests.reviews.ReviewRequest
import com.mho.training.data.translators.toDomainReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewDataSource : RemoteReviewDataSource {

    override suspend fun getReviewList(movieId: Int): List<Review> = withContext(Dispatchers.IO) {
        ReviewRequest(movieId)
            .requestReviewList()
            .map { it.toDomainReview() }
    }
}