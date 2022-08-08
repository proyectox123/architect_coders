package com.mho.training.features.reviews

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewStateMachine(
    intentInterpreter: IntentInterpreterForReview,
    actionProcessor: ActionProcessorForReview,
    reducer: ViewStateReducerForReview,
) : StateMachineForReview(
    intentInterpreter,
    actionProcessor,
    reducer,
    ReviewIntent.LoadAllReviewIntent,
    ReviewViewState.idle()
)
