package com.mho.training.data.remote.requests

import com.mho.training.data.remote.requests.reviews.ReviewRequest
import com.mho.training.data.source.RemoteReviewDataSource
import com.mho.training.data.translators.toDomainReview
import com.mho.training.domain.Review

class ReviewDataSource : RemoteReviewDataSource {

    override suspend fun getReviewList(movieId: Int): List<Review> =
        ReviewRequest(movieId)
            .requestReviewList()
            .map { it.toDomainReview() }
}