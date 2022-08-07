package com.mho.training.features.reviews

import com.example.android.data.repositories.ReviewRepository
import com.example.android.usecases.GetReviewListUseCase
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
    fun intentProcessorForReviewProvider(): IntentProcessorForReview =
        ReviewIntentProcessor(movieId)

    @Provides
    fun actionInterpreterForReviewProvider(
        getReviewListUseCase: GetReviewListUseCase
    ): ActionInterpreterForReview =
        ReviewActionInterpreter(getReviewListUseCase)

    @Provides
    fun viewStateReducerForReviewProvider(): ViewStateReducerForReview = ReviewViewStateReducer()

    @Provides
    fun stateMachineForReviewProvider(
        intentProcessor: IntentProcessorForReview,
        actionInterpreter: ActionInterpreterForReview,
        reducer: ViewStateReducerForReview
    ): StateMachineForReview =
        ReviewStateMachine(intentProcessor, actionInterpreter, reducer)

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
