package com.mho.training.adapters.trailer

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Trailer
import com.mho.training.databinding.ItemTrailerBinding
import com.mho.training.features.trailers.mvi.TrailerIntent
import com.mho.training.mviandroid.utils.safeOffer
import kotlinx.coroutines.channels.SendChannel

class TrailerViewHolder(
    private val dataBinding: ItemTrailerBinding,
    private val sendChannel: SendChannel<TrailerIntent.OpenTrailerIntent>,
) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(trailer: Trailer){
        dataBinding.trailer = trailer
        itemView.setOnClickListener {
            sendChannel.safeOffer(TrailerIntent.OpenTrailerIntent(trailer))
        }
    }

}