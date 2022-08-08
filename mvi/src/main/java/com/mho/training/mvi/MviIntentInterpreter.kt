package com.mho.training.mvi

interface MviIntentInterpreter<in I : MviIntent, out A : MviAction> {
    fun intentToAction(intent: I): A
}
