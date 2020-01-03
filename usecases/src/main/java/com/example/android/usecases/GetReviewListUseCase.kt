package com.example.android.usecases

import com.example.android.data.repositories.ReviewRepository
import com.example.android.domain.Review

class GetReviewListUseCase(private val reviewRepository: ReviewRepository){

    suspend fun invoke(movieId: Int): List<Review> =
        reviewRepository.getReviewList(movieId)
}