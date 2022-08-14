package com.mho.training.features.trailer.mvi.viewmodel

import com.mho.training.features.trailer.mvi.alias.IntentInterpreterForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerIntent
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerIntentInterpreter(
    private val movieId: Int,
) : IntentInterpreterForTrailer {

    override fun intentToAction(intent: TrailerIntent): TrailerAction =
        when (intent) {
            TrailerIntent.LoadAllTrailerIntent -> TrailerAction.LoadAllTrailerAction(movieId)
            is TrailerIntent.OpenTrailerIntent -> TrailerAction.OpenTrailerAction(intent.trailer)
        }
}
