package com.mho.training.features.reviews.mvi

import com.mho.training.features.reviews.alias.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewStateMachine(
    intentInterpreter: IntentInterpreterForReview,
    actionProcessor: ActionProcessorForReview,
    reducer: ViewStateReducerForReview,
    sideEffectFactory: SideEffectFactoryForReview,
) : StateMachineForReview(
    intentInterpreter,
    actionProcessor,
    reducer,
    sideEffectFactory,
    ReviewViewState.idle(),
    ReviewSideEffect.NoOp,
)
