package com.mho.training.features.trailer.mvi.viewmodel

import com.mho.training.features.trailer.mvi.alias.ViewStateReducerForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerResult
import com.mho.training.features.trailer.mvi.states.TrailerViewState
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerViewStateReducer : ViewStateReducerForTrailer {

    override fun reduce(
        previous: TrailerViewState,
        result: TrailerResult
    ): TrailerViewState = when(result) {
        TrailerResult.LoadAllTrailerResult.Loading -> previous.copy(isLoading = true)
        is TrailerResult.LoadAllTrailerResult.Failure ->
            previous.copy(isLoading = false, error = result.error, trailers = emptyList())
        is TrailerResult.LoadAllTrailerResult.Success ->
            previous.copy(isLoading = false, error = null, trailers = result.trailers)
        is TrailerResult.OpenTrailerResult.Success -> previous
    }
}
