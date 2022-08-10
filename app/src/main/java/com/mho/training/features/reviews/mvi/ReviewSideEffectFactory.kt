package com.mho.training.features.reviews.mvi

import com.mho.training.features.reviews.alias.SideEffectFactoryForReview
import kotlinx.coroutines.FlowPreview

@FlowPreview
class ReviewSideEffectFactory : SideEffectFactoryForReview {

    override fun resultToSideEffect(result: ReviewResult): ReviewSideEffect =
        when (result) {
            is ReviewResult.OpenReviewResult.Success -> ReviewSideEffect.OpenReview(result.review)
            else -> ReviewSideEffect.NoOp
        }
}