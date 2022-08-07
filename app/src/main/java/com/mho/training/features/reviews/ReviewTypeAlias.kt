@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.reviews

import com.mho.training.mvi.MviIntentProcessor
import com.mho.training.mvi.MviStateMachine
import com.mho.training.mvi.MviViewStateReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
typealias StateMachineForReview =
        @JvmSuppressWildcards MviStateMachine<ReviewIntent, ReviewViewState, ReviewResult>

@FlowPreview
typealias ViewStateReducerForReview =
        @JvmSuppressWildcards MviViewStateReducer<ReviewViewState, ReviewResult>

@FlowPreview
typealias IntentProcessorForReview =
        @JvmSuppressWildcards MviIntentProcessor<ReviewIntent, ReviewResult>
