package com.mho.training.features.reviews.mvi

import com.example.android.domain.Review
import com.mho.training.mvi.Mvi

data class ReviewViewState(
    val isLoading: Boolean,
    val reviews: List<Review>,
    val error: Throwable?
) : Mvi.ViewState {
    companion object {
        fun idle() : ReviewViewState = ReviewViewState(
            isLoading = false,
            reviews = emptyList(),
            error = null
        )
    }
}
