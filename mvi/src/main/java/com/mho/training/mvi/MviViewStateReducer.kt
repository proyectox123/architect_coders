package com.mho.training.mvi

interface MviViewStateReducer<S : Mvi.ViewState, R : Mvi.Result> {
    fun reduce(previous: S, result: R): S
}
