package com.mho.training.features.trailers.mvi

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

data class TrailerViewState(
    val isLoading: Boolean,
    val trailers: List<Trailer>,
    val error: Throwable?
) : Mvi.ViewState {
    companion object {
        fun idle() : TrailerViewState = TrailerViewState(
            isLoading = false,
            trailers = emptyList(),
            error = null
        )
    }
}
