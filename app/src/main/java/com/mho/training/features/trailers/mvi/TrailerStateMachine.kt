package com.mho.training.features.trailers.mvi

import com.mho.training.features.trailers.alias.*
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
