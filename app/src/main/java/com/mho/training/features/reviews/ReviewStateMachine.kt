package com.mho.training.features.reviews

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewStateMachine(
    intentProcessor: IntentProcessorForReview,
    reducer: ViewStateReducerForReview,
) : StateMachineForReview(
    intentProcessor,
    reducer,
    ReviewIntent.LoadAllReviewIntent,
    ReviewViewState.idle()
)
