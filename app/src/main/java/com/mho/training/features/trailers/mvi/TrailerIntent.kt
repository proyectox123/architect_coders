package com.mho.training.features.trailers.mvi

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

sealed class TrailerIntent : Mvi.Intent {
    object LoadAllTrailerIntent: TrailerIntent()
    data class OpenTrailerIntent(val trailer: Trailer): TrailerIntent()
}
