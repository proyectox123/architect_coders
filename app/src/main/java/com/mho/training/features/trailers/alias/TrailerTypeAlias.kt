@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.trailers.alias

import com.mho.training.features.trailers.mvi.*
import com.mho.training.mvi.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
typealias IntentInterpreterForTrailer =
        @JvmSuppressWildcards MviIntentInterpreter<TrailerIntent, TrailerAction>

@FlowPreview
typealias ActionProcessorForTrailer =
        @JvmSuppressWildcards MviActionProcessor<TrailerAction, TrailerResult>

@FlowPreview
typealias ViewStateReducerForTrailer =
        @JvmSuppressWildcards MviViewStateReducer<TrailerViewState, TrailerResult>

@FlowPreview
typealias SideEffectFactoryForTrailer =
        @JvmSuppressWildcards MviSideEffectFactory<TrailerResult, TrailerSideEffect>

@FlowPreview
typealias StateMachineForTrailer = @JvmSuppressWildcards MviStateMachine<
        TrailerIntent,
        TrailerAction,
        TrailerViewState,
        TrailerResult,
        TrailerSideEffect
        >
