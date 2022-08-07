package com.mho.training.features.reviews

import com.example.android.domain.Review
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetReviewListUseCase
import com.mho.training.features.reviews.ReviewIntent.LoadAllReviewIntent
import com.mho.training.features.reviews.ReviewIntent.OpenReviewIntent
import com.mho.training.features.reviews.ReviewResult.LoadAllReviewResult
import com.mho.training.features.reviews.ReviewResult.OpenReviewResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

@FlowPreview
class ReviewIntentProcessor(
    private val movieId: Int,
    private val getReviewListUseCase: GetReviewListUseCase,
) : IntentProcessorForReview {

    override fun intentToResult(viewIntent: ReviewIntent): Flow<ReviewResult> =
        when (viewIntent) {
            LoadAllReviewIntent -> loadAllReview()
            OpenReviewIntent -> openReview()
        }

    private fun loadAllReview() : Flow<ReviewResult> =
        flow {
            emit(validateReviewListUseCase(getReviewListUseCase.invoke(movieId)))
        }.onStart {
            emit(LoadAllReviewResult.Loading)
        }.catch { error ->
            emit(LoadAllReviewResult.Failure(error))
        }

    private fun openReview() : Flow<ReviewResult> = flow {
        emit(OpenReviewResult.Success)
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
