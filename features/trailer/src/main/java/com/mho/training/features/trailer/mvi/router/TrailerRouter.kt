package com.mho.training.features.trailer.mvi.router

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.android.domain.Trailer
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import com.mho.training.mvi.MviRouter

class TrailerRouter(
    private val activity: Activity,
): MviRouter<TrailerSideEffect> {

    override fun route(sideEffect: TrailerSideEffect) {
        when(sideEffect) {
            is TrailerSideEffect.OpenTrailer -> openTrailer(sideEffect.trailer)
            TrailerSideEffect.NoOp -> { }
        }
    }

    private fun openTrailer(trailer: Trailer) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(trailer.videoPath)
        }

        activity.startActivity(intent)
    }
}
