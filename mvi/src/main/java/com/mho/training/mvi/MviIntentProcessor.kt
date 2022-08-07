package com.mho.training.mvi

interface MviIntentProcessor<in I : MviIntent, out A : MviAction> {
    fun intentToAction(intent: I): A
}
