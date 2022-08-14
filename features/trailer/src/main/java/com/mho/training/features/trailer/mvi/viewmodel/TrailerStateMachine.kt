package com.mho.training.features.trailer.mvi.viewmodel

import com.mho.training.features.trailer.mvi.alias.ActionProcessorForTrailer
import com.mho.training.features.trailer.mvi.alias.IntentInterpreterForTrailer
import com.mho.training.features.trailer.mvi.alias.SideEffectFactoryForTrailer
import com.mho.training.features.trailer.mvi.alias.StateMachineForTrailer
import com.mho.training.features.trailer.mvi.alias.ViewStateReducerForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import com.mho.training.features.trailer.mvi.states.TrailerViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TrailerStateMachine(
    intentInterpreter: IntentInterpreterForTrailer,
    actionProcessor: ActionProcessorForTrailer,
    reducer: ViewStateReducerForTrailer,
    sideEffectFactory: SideEffectFactoryForTrailer,
) : StateMachineForTrailer(
    intentInterpreter,
    actionProcessor,
    reducer,
    sideEffectFactory,
    TrailerViewState.idle(),
    TrailerSideEffect.NoOp,
)
