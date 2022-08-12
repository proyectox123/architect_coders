package com.mho.training.features.trailers

import com.mho.training.features.trailers.alias.StateMachineForTrailer
import com.mho.training.features.trailers.mvi.*
import com.mho.training.mviandroid.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TrailersViewModel(
    stateMachine: StateMachineForTrailer,
    uiDispatcher: CoroutineDispatcher
) : MviViewModel<TrailerIntent, TrailerAction, TrailerViewState, TrailerResult, TrailerSideEffect>(
    stateMachine,
    uiDispatcher,
)
