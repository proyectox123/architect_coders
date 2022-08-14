package com.mho.training.features.trailer.mvi.viewmodel

import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetTrailerListUseCase
import com.mho.training.features.trailer.mvi.alias.ActionProcessorForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

@FlowPreview
class TrailerActionProcessor(
    private val getTrailerListUseCase: GetTrailerListUseCase,
) : ActionProcessorForTrailer {

    override fun actionToResult(action: TrailerAction): Flow<TrailerResult> =
        when (action) {
            is TrailerAction.LoadAllTrailerAction -> loadAllTrailer(action.movieId)
            is TrailerAction.OpenTrailerAction -> openTrailer(action.trailer)
        }

    private fun loadAllTrailer(movieId: Int): Flow<TrailerResult> =
        flow {
            emit(validateTrailerListUseCase(getTrailerListUseCase.invoke(movieId)))
        }.onStart {
            emit(TrailerResult.LoadAllTrailerResult.Loading)
        }.catch { error ->
            emit(TrailerResult.LoadAllTrailerResult.Failure(error))
        }

    private fun openTrailer(trailer: Trailer): Flow<TrailerResult> = flow {
        emit(TrailerResult.OpenTrailerResult.Success(trailer))
    }

    private fun validateTrailerListUseCase(
        trailerListUseCaseDataResult: DataResult<List<Trailer>>
    ): TrailerResult =
        when (trailerListUseCaseDataResult) {
            is DataResult.Error ->
                TrailerResult.LoadAllTrailerResult.Failure(trailerListUseCaseDataResult.exception)
            is DataResult.Success ->
                TrailerResult.LoadAllTrailerResult.Success(trailerListUseCaseDataResult.data)
        }
}
