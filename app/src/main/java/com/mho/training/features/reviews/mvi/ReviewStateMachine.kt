package com.mho.training.features.reviews.mvi

import com.mho.training.features.reviews.alias.ActionProcessorForReview
import com.mho.training.features.reviews.alias.IntentInterpreterForReview
import com.mho.training.features.reviews.alias.StateMachineForReview
import com.mho.training.features.reviews.alias.ViewStateReducerForReview
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
    ReviewViewState.idle()
)
