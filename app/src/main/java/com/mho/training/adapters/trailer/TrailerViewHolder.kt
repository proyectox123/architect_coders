package com.mho.training.adapters.trailer

import androidx.recyclerview.widget.RecyclerView
import com.mho.training.databinding.ItemTrailerBinding
import com.mho.training.domain.Trailer

class TrailerViewHolder(private val dataBinding: ItemTrailerBinding,
                       private val listener: (Trailer) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(trailer: Trailer){
        dataBinding.trailer = trailer
        itemView.setOnClickListener { listener(trailer) }
    }

}