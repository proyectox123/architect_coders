package com.mho.training.adapters.review

import androidx.recyclerview.widget.RecyclerView
import com.mho.training.data.remote.models.Review
import com.mho.training.databinding.ItemReviewBinding

class ReviewViewHolder(private val dataBinding: ItemReviewBinding,
                       private val listener: (Review) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(review: Review){
        dataBinding.review = review
        itemView.setOnClickListener { listener(review) }
    }

}