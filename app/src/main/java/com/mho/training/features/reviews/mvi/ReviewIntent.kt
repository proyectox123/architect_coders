package com.mho.training.features.reviews.mvi

import com.example.android.domain.Review
import com.mho.training.mvi.Mvi

sealed class ReviewIntent : Mvi.Intent {
    object LoadAllReviewIntent: ReviewIntent()
    data class OpenReviewIntent(val review: Review): ReviewIntent()
}
