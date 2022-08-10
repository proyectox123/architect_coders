package com.mho.training.adapters.review

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Review
import com.mho.training.R
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate
import kotlinx.coroutines.channels.SendChannel

class ReviewListAdapter(
    private val sendChannel: SendChannel<ReviewIntent.OpenReviewIntent>
) : RecyclerView.Adapter<ReviewViewHolder>() {

    var reviews: List<Review> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(parent.bindingInflate(R.layout.item_review, false), sendChannel)

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
}