package com.mho.training.features.trailers.mvi

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

sealed class TrailerSideEffect : Mvi.SideEffect {
    object NoOp : TrailerSideEffect()
    data class OpenTrailer(val trailer: Trailer) : TrailerSideEffect()
}
