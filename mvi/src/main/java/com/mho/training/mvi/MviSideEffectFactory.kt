package com.mho.training.mvi

interface MviSideEffectFactory<R : Mvi.Result, SE : Mvi.SideEffect> {
    fun resultToSideEffect(result: R): SE
}
