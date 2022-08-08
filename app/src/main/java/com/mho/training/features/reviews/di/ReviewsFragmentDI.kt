package com.mho.training.features.reviews.di

import com.example.android.data.repositories.ReviewRepository
import com.example.android.usecases.GetReviewListUseCase
import com.mho.training.features.reviews.ReviewsViewModel
import com.mho.training.features.reviews.alias.ActionProcessorForReview
import com.mho.training.features.reviews.alias.IntentInterpreterForReview
import com.mho.training.features.reviews.alias.StateMachineForReview
import com.mho.training.features.reviews.alias.ViewStateReducerForReview
import com.mho.training.features.reviews.mvi.ReviewActionProcessor
import com.mho.training.features.reviews.mvi.ReviewIntentInterpreter
import com.mho.training.features.reviews.mvi.ReviewStateMachine
import com.mho.training.features.reviews.mvi.ReviewViewStateReducer
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Module
class ReviewsFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun intentInterpreterForReviewProvider(): IntentInterpreterForReview =
        ReviewIntentInterpreter(movieId)

    @Provides
    fun actionProcessorForReviewProvider(
        getReviewListUseCase: GetReviewListUseCase
    ): ActionProcessorForReview =
        ReviewActionProcessor(getReviewListUseCase)

    @Provides
    fun viewStateReducerForReviewProvider(): ViewStateReducerForReview = ReviewViewStateReducer()

    @Provides
    fun stateMachineForReviewProvider(
        intentInterpreter: IntentInterpreterForReview,
        actionProcessor: ActionProcessorForReview,
        reducer: ViewStateReducerForReview
    ): StateMachineForReview =
        ReviewStateMachine(intentInterpreter, actionProcessor, reducer)

    @Provides
    fun reviewsViewModelProvider(
        reviewStateMachine: StateMachineForReview
    ) = ReviewsViewModel(
        reviewStateMachine,
        Dispatchers.Main
    )

    @Provides
    fun getReviewListUseCaseProvider(reviewRepository: ReviewRepository) =
        GetReviewListUseCase(reviewRepository)
}

@ExperimentalCoroutinesApi
@FlowPreview
@Subcomponent(modules = [(ReviewsFragmentModule::class)])
interface ReviewsFragmentComponent {
    val reviewsViewModel: ReviewsViewModel
}
