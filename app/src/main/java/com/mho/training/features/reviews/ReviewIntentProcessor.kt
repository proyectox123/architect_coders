package com.mho.training.features.reviews

import com.mho.training.features.reviews.ReviewAction.LoadAllReviewAction
import com.mho.training.features.reviews.ReviewAction.OpenReviewAction
import com.mho.training.features.reviews.ReviewIntent.LoadAllReviewIntent
import com.mho.training.features.reviews.ReviewIntent.OpenReviewIntent
import kotlinx.coroutines.FlowPreview

@FlowPreview
class ReviewIntentProcessor(
    private val movieId: Int,
) : IntentProcessorForReview {

    override fun intentToAction(intent: ReviewIntent): ReviewAction =
        when (intent) {
            LoadAllReviewIntent -> LoadAllReviewAction(movieId)
            OpenReviewIntent -> OpenReviewAction(movieId)
        }
}
