package com.mho.training.features.trailer.mvi.viewmodel

import com.mho.training.features.trailer.mvi.alias.SideEffectFactoryForTrailer
import com.mho.training.features.trailer.mvi.states.TrailerResult
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerSideEffectFactory : SideEffectFactoryForTrailer {

    override fun resultToSideEffect(result: TrailerResult): TrailerSideEffect =
        when (result) {
            is TrailerResult.OpenTrailerResult.Success -> TrailerSideEffect.OpenTrailer(result.trailer)
            else -> TrailerSideEffect.NoOp
        }
}
