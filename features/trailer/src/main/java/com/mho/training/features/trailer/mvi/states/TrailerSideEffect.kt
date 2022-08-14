package com.mho.training.features.trailer.mvi.states

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

sealed class TrailerSideEffect : Mvi.SideEffect {
    object NoOp : TrailerSideEffect()
    data class OpenTrailer(val trailer: Trailer) : TrailerSideEffect()
}
