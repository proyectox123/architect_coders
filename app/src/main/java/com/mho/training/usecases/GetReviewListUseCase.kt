package com.mho.training.usecases

import com.mho.training.data.ReviewRepository
import com.mho.training.domain.Review

class GetReviewListUseCase(private val reviewRepository: ReviewRepository){

    suspend fun invoke(movieId: Int): List<Review> =
        reviewRepository.getReviewList(movieId)
}