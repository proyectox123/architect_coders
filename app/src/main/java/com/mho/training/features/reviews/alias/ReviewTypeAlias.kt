@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.reviews.alias

import com.mho.training.features.reviews.mvi.*
import com.mho.training.mvi.*
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
typealias SideEffectFactoryForReview =
        @JvmSuppressWildcards MviSideEffectFactory<ReviewResult, ReviewSideEffect>

@FlowPreview
typealias StateMachineForReview = @JvmSuppressWildcards MviStateMachine<
        ReviewIntent,
        ReviewAction,
        ReviewViewState,
        ReviewResult,
        ReviewSideEffect
        >
