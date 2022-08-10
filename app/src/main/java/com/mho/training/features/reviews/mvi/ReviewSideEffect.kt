package com.mho.training.features.reviews.mvi

import com.example.android.domain.Review
import com.mho.training.mvi.Mvi

sealed class ReviewSideEffect : Mvi.SideEffect {
    object NoOp : ReviewSideEffect()
    data class OpenReview(val review: Review) : ReviewSideEffect()
}
