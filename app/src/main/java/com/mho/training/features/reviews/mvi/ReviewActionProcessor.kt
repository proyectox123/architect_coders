package com.mho.training.features.reviews.mvi

import com.example.android.domain.Review
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetReviewListUseCase
import com.mho.training.features.reviews.alias.ActionProcessorForReview
import com.mho.training.features.reviews.mvi.ReviewAction.LoadAllReviewAction
import com.mho.training.features.reviews.mvi.ReviewAction.OpenReviewAction
import com.mho.training.features.reviews.mvi.ReviewResult.LoadAllReviewResult
import com.mho.training.features.reviews.mvi.ReviewResult.OpenReviewResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

@FlowPreview
class ReviewActionProcessor(
    private val getReviewListUseCase: GetReviewListUseCase,
) : ActionProcessorForReview {

    override fun actionToResult(action: ReviewAction): Flow<ReviewResult> =
        when (action) {
            is LoadAllReviewAction -> loadAllReview(action.movieId)
            is OpenReviewAction -> openReview(action.review)
        }

    private fun loadAllReview(movieId: Int) : Flow<ReviewResult> =
        flow {
            emit(validateReviewListUseCase(getReviewListUseCase.invoke(movieId)))
        }.onStart {
            emit(LoadAllReviewResult.Loading)
        }.catch { error ->
            emit(LoadAllReviewResult.Failure(error))
        }

    private fun openReview(review: Review) : Flow<ReviewResult> = flow {
        emit(OpenReviewResult.Success(review))
    }

    private fun validateReviewListUseCase(
        reviewListUseCaseDataResult: DataResult<List<Review>>
    ): ReviewResult =
        when (reviewListUseCaseDataResult) {
            is DataResult.Error ->
                LoadAllReviewResult.Failure(reviewListUseCaseDataResult.exception)
            is DataResult.Success ->
                LoadAllReviewResult.Success(reviewListUseCaseDataResult.data)
        }
}
