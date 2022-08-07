package com.mho.training.features.reviews

import com.example.android.domain.Review
import com.mho.training.mvi.MviResult

sealed class ReviewResult : MviResult {

    sealed class LoadAllReviewResult: ReviewResult() {
        object Loading : LoadAllReviewResult()
        data class Success(val reviews: List<Review>): LoadAllReviewResult()
        data class Failure(val error: Throwable): LoadAllReviewResult()
    }

    sealed class OpenReviewResult: ReviewResult() {
        object Success : OpenReviewResult()
    }
}
