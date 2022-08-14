@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.mho.training.features.trailer.mvi.alias

import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerIntent
import com.mho.training.features.trailer.mvi.states.TrailerResult
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import com.mho.training.features.trailer.mvi.states.TrailerViewState
import com.mho.training.mvi.MviActionProcessor
import com.mho.training.mvi.MviIntentInterpreter
import com.mho.training.mvi.MviSideEffectFactory
import com.mho.training.mvi.MviStateMachine
import com.mho.training.mvi.MviViewStateReducer
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
