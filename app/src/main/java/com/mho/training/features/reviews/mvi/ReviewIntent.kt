package com.mho.training.features.reviews.mvi

import com.mho.training.mvi.Mvi

sealed class ReviewIntent : Mvi.Intent {
    object LoadAllReviewIntent: ReviewIntent()
    object OpenReviewIntent: ReviewIntent()
}
