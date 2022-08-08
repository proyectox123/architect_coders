package com.mho.training.features.reviews.mvi

import com.mho.training.mvi.MviIntent

sealed class ReviewIntent : MviIntent {
    object LoadAllReviewIntent: ReviewIntent()
    object OpenReviewIntent: ReviewIntent()
}
