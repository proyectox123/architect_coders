package com.mho.training.adapters.review

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.domain.Review
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class ReviewListAdapter(private val listener: (Review) -> Unit) :
    RecyclerView.Adapter<ReviewViewHolder>() {

    var reviews: List<Review> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(parent.bindingInflate(R.layout.item_review, false), listener)

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
}