@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.reviews.alias

import com.mho.training.features.reviews.mvi.ReviewAction
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.features.reviews.mvi.ReviewResult
import com.mho.training.features.reviews.mvi.ReviewViewState
import com.mho.training.mvi.MviActionProcessor
import com.mho.training.mvi.MviIntentInterpreter
import com.mho.training.mvi.MviStateMachine
import com.mho.training.mvi.MviViewStateReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
typealias IntentInterpreterForReview =
        @JvmSuppressWildcards MviIntentInterpreter<ReviewIntent, ReviewAction>

@FlowPreview
typealias ActionProcessorForReview =
        @JvmSuppressWildcards MviActionProcessor<ReviewAction, ReviewResult>

@FlowPreview
typealias ViewStateReducerForReview =
        @JvmSuppressWildcards MviViewStateReducer<ReviewViewState, ReviewResult>

@FlowPreview
typealias StateMachineForReview =
        @JvmSuppressWildcards MviStateMachine<ReviewIntent, ReviewAction, ReviewViewState, ReviewResult>
