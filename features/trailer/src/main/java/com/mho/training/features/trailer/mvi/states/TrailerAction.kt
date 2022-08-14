package com.mho.training.features.trailer.mvi.states

import com.example.android.domain.Trailer
import com.mho.training.mvi.Mvi

sealed class TrailerAction : Mvi.Action {
    data class LoadAllTrailerAction(val movieId: Int) : TrailerAction()
    data class OpenTrailerAction(val trailer: Trailer) : TrailerAction()
}
