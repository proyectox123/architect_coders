package com.mho.training.mvi

interface MviViewStateReducer<S : MviViewState, R : MviResult> {
    fun reduce(previous: S, result: R): S
}
