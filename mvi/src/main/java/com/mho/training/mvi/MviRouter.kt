package com.mho.training.mvi

interface MviRouter<SE : Mvi.SideEffect> {
    fun route(sideEffect: SE)
}
