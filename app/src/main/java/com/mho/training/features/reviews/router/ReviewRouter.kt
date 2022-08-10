package com.mho.training.features.reviews.router

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.android.domain.Review
import com.mho.training.features.reviews.mvi.ReviewSideEffect
import com.mho.training.mvi.MviRouter

class ReviewRouter(
    private val activity: Activity,
): MviRouter<ReviewSideEffect> {

    override fun route(sideEffect: ReviewSideEffect) {
        when(sideEffect) {
            is ReviewSideEffect.OpenReview -> openReview(sideEffect.review)
            ReviewSideEffect.NoOp -> { }
        }
    }

    private fun openReview(review: Review) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(review.url)
        }

        activity.startActivity(intent)
    }
}
