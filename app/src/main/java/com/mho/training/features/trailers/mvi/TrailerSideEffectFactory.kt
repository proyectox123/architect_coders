package com.mho.training.features.trailers.mvi

import com.mho.training.features.trailers.alias.SideEffectFactoryForTrailer
import kotlinx.coroutines.FlowPreview

@FlowPreview
class TrailerSideEffectFactory : SideEffectFactoryForTrailer {

    override fun resultToSideEffect(result: TrailerResult): TrailerSideEffect =
        when (result) {
            is TrailerResult.OpenTrailerResult.Success -> TrailerSideEffect.OpenTrailer(result.trailer)
            else -> TrailerSideEffect.NoOp
        }
}