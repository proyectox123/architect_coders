@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.reviews

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
