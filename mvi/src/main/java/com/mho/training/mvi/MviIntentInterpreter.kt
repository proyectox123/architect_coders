package com.mho.training.mvi

interface MviIntentInterpreter<in I : Mvi.Intent, out A : Mvi.Action> {
    fun intentToAction(intent: I): A
}
