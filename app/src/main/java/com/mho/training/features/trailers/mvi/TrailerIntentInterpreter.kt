package com.mho.training.features.trailers.mvi

import com.mho.training.features.trailers.alias.IntentInterpreterForTrailer
import com.mho.training.features.trailers.mvi.TrailerAction.LoadAllTrailerAction
import com.mho.training.features.trailers.mvi.TrailerAction.OpenTrailerAction
import com.mho.training.features.trailers.mvi.TrailerIntent.LoadAllTrailerIntent
import com.mho.training.features.trailers.mvi.TrailerIntent.OpenTrailerIntent
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerIntentInterpreter(
    private val movieId: Int,
) : IntentInterpreterForTrailer {

    override fun intentToAction(intent: TrailerIntent): TrailerAction =
        when (intent) {
            LoadAllTrailerIntent -> LoadAllTrailerAction(movieId)
            is OpenTrailerIntent -> OpenTrailerAction(intent.trailer)
        }
}
