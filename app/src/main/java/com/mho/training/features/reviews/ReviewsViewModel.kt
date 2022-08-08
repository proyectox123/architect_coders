package com.mho.training.features.reviews

import com.mho.training.features.reviews.alias.StateMachineForReview
import com.mho.training.features.reviews.mvi.ReviewAction
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.features.reviews.mvi.ReviewResult
import com.mho.training.features.reviews.mvi.ReviewViewState
import com.mho.training.mviandroid.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewsViewModel(
    reviewStateMachine: StateMachineForReview,
    uiDispatcher: CoroutineDispatcher
) : MviViewModel<ReviewIntent, ReviewAction, ReviewViewState, ReviewResult>(
    reviewStateMachine,
    uiDispatcher
)
