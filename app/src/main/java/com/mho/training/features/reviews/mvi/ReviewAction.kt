package com.mho.training.features.reviews.mvi

import com.mho.training.mvi.Mvi

sealed class ReviewAction : Mvi.Action {
    data class LoadAllReviewAction(val movieId: Int): ReviewAction()
    data class OpenReviewAction(val movieId: Int): ReviewAction()
}
