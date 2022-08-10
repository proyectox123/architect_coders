package com.mho.training.adapters.review

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Review
import com.mho.training.databinding.ItemReviewBinding
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.mviandroid.utils.safeOffer
import kotlinx.coroutines.channels.SendChannel

class ReviewViewHolder(
    private val dataBinding: ItemReviewBinding,
    private val sendChannel: SendChannel<ReviewIntent.OpenReviewIntent>,
) : RecyclerView.ViewHolder(dataBinding.root){

    fun bind(review: Review){
        dataBinding.review = review
        itemView.setOnClickListener {
            sendChannel.safeOffer(ReviewIntent.OpenReviewIntent(review))
        }
    }
}