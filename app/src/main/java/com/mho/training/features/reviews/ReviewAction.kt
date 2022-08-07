package com.mho.training.features.reviews

import com.mho.training.mvi.MviAction

sealed class ReviewAction : MviAction {
    data class LoadAllReviewAction(val movieId: Int): ReviewAction()
    data class OpenReviewAction(val movieId: Int): ReviewAction()
}
