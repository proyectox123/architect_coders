package com.mho.training.features.trailer.mvi.viewmodel

import com.mho.training.features.trailer.mvi.alias.StateMachineForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerIntent
import com.mho.training.features.trailer.mvi.states.TrailerResult
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import com.mho.training.features.trailer.mvi.states.TrailerViewState
import com.mho.training.mviandroid.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TrailerViewModel(
    stateMachine: StateMachineForTrailer,
    uiDispatcher: CoroutineDispatcher
) : MviViewModel<TrailerIntent, TrailerAction, TrailerViewState, TrailerResult, TrailerSideEffect>(
    stateMachine,
    uiDispatcher,
)
