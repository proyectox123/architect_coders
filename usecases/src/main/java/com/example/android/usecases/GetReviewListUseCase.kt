package com.example.android.usecases

import com.example.android.data.repositories.ReviewRepository
import com.example.android.domain.Review
import com.example.android.framework.data.remote.requests.Result

class GetReviewListUseCase(private val reviewRepository: ReviewRepository){

    suspend fun invoke(movieId: Int): Result<List<Review>> =
        reviewRepository.getReviewList(movieId)
}