package com.mho.training.features.trailers.mvi

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

sealed class TrailerResult : Mvi.Result {

    sealed class LoadAllTrailerResult: TrailerResult() {
        object Loading : LoadAllTrailerResult()
        data class Success(val trailers: List<Trailer>): LoadAllTrailerResult()
        data class Failure(val error: Throwable): LoadAllTrailerResult()
    }

    sealed class OpenTrailerResult: TrailerResult() {
        data class Success(val trailer: Trailer) : OpenTrailerResult()
    }
}
