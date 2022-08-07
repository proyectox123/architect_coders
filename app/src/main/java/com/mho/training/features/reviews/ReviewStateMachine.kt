package com.mho.training.features.reviews

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewStateMachine(
    intentProcessor: IntentProcessorForReview,
    actionInterpreter: ActionInterpreterForReview,
    reducer: ViewStateReducerForReview,
) : StateMachineForReview(
    intentProcessor,
    actionInterpreter,
    reducer,
    ReviewIntent.LoadAllReviewIntent,
    ReviewViewState.idle()
)
