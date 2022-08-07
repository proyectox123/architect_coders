package com.mho.training.features.reviews

import com.example.android.domain.Review
import com.mho.training.mvi.MviViewState

data class ReviewViewState(
    val isLoading: Boolean,
    val reviews: List<Review>,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle() : ReviewViewState = ReviewViewState(
            isLoading = false,
            reviews = emptyList(),
            error = null
        )
    }
}
