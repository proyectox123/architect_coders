package com.mho.training.features.trailers.mvi

import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetTrailerListUseCase
import com.mho.training.features.trailers.alias.ActionProcessorForTrailer
import com.mho.training.features.trailers.mvi.TrailerAction.LoadAllTrailerAction
import com.mho.training.features.trailers.mvi.TrailerAction.OpenTrailerAction
import com.mho.training.features.trailers.mvi.TrailerResult.LoadAllTrailerResult
import com.mho.training.features.trailers.mvi.TrailerResult.OpenTrailerResult
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
            is LoadAllTrailerAction -> loadAllTrailer(action.movieId)
            is OpenTrailerAction -> openTrailer(action.trailer)
        }

    private fun loadAllTrailer(movieId: Int) : Flow<TrailerResult> =
        flow {
            emit(validateTrailerListUseCase(getTrailerListUseCase.invoke(movieId)))
        }.onStart {
            emit(LoadAllTrailerResult.Loading)
        }.catch { error ->
            emit(LoadAllTrailerResult.Failure(error))
        }

    private fun openTrailer(trailer: Trailer) : Flow<TrailerResult> = flow {
        emit(OpenTrailerResult.Success(trailer))
    }

    private fun validateTrailerListUseCase(
        trailerListUseCaseDataResult: DataResult<List<Trailer>>
    ): TrailerResult =
        when (trailerListUseCaseDataResult) {
            is DataResult.Error ->
                LoadAllTrailerResult.Failure(trailerListUseCaseDataResult.exception)
            is DataResult.Success ->
                LoadAllTrailerResult.Success(trailerListUseCaseDataResult.data)
        }
}
