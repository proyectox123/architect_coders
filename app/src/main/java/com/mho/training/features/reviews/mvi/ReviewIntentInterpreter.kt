package com.mho.training.features.reviews.mvi

import com.mho.training.features.reviews.alias.IntentInterpreterForReview
import com.mho.training.features.reviews.mvi.ReviewAction.LoadAllReviewAction
import com.mho.training.features.reviews.mvi.ReviewAction.OpenReviewAction
import com.mho.training.features.reviews.mvi.ReviewIntent.LoadAllReviewIntent
import com.mho.training.features.reviews.mvi.ReviewIntent.OpenReviewIntent
import kotlinx.coroutines.FlowPreview

@FlowPreview
class ReviewIntentInterpreter(
    private val movieId: Int,
) : IntentInterpreterForReview {

    override fun intentToAction(intent: ReviewIntent): ReviewAction =
        when (intent) {
            LoadAllReviewIntent -> LoadAllReviewAction(movieId)
            OpenReviewIntent -> OpenReviewAction(movieId)
        }
}
