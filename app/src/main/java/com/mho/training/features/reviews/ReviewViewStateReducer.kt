package com.mho.training.features.reviews

import com.mho.training.features.reviews.ReviewResult.LoadAllReviewResult
import com.mho.training.features.reviews.ReviewResult.OpenReviewResult
import kotlinx.coroutines.FlowPreview

@FlowPreview
class ReviewViewStateReducer : ViewStateReducerForReview {

    override fun reduce(
        previous: ReviewViewState,
        result: ReviewResult
    ): ReviewViewState = when(result) {
        LoadAllReviewResult.Loading -> previous.copy(isLoading = true)
        is LoadAllReviewResult.Failure ->
            previous.copy(isLoading = false, error = result.error, reviews = emptyList())
        is LoadAllReviewResult.Success ->
            previous.copy(isLoading = false, error = null, reviews = result.reviews)
        OpenReviewResult.Success -> previous.copy(isLoading = false)
    }
}
