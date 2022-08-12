package com.mho.training.features.trailers.mvi

import com.mho.training.features.trailers.alias.ViewStateReducerForTrailer
import com.mho.training.features.trailers.mvi.TrailerResult.LoadAllTrailerResult
import com.mho.training.features.trailers.mvi.TrailerResult.OpenTrailerResult
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerViewStateReducer : ViewStateReducerForTrailer {

    override fun reduce(
        previous: TrailerViewState,
        result: TrailerResult
    ): TrailerViewState = when(result) {
        LoadAllTrailerResult.Loading -> previous.copy(isLoading = true)
        is LoadAllTrailerResult.Failure ->
            previous.copy(isLoading = false, error = result.error, trailers = emptyList())
        is LoadAllTrailerResult.Success ->
            previous.copy(isLoading = false, error = null, trailers = result.trailers)
        is OpenTrailerResult.Success -> previous
    }
}
