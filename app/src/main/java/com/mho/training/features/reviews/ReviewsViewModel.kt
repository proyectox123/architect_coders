package com.mho.training.features.reviews

import com.mho.training.features.reviews.alias.StateMachineForReview
import com.mho.training.features.reviews.mvi.*
import com.mho.training.mviandroid.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewsViewModel(
    stateMachine: StateMachineForReview,
    uiDispatcher: CoroutineDispatcher
) : MviViewModel<ReviewIntent, ReviewAction, ReviewViewState, ReviewResult, ReviewSideEffect>(
    stateMachine,
    uiDispatcher,
)
