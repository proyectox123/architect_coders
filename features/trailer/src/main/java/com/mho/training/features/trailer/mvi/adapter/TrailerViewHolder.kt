package com.mho.training.features.trailer.mvi.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Trailer
import com.mho.training.features.trailer.databinding.ItemTrailerBinding
import com.mho.training.features.trailer.mvi.states.TrailerIntent
import com.mho.training.mviandroid.utils.safeOffer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel

@ExperimentalCoroutinesApi
class TrailerViewHolder(
    private val dataBinding: ItemTrailerBinding,
    private val sendChannel: SendChannel<TrailerIntent.OpenTrailerIntent>,
) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(trailer: Trailer) {
        dataBinding.trailer = trailer
        itemView.setOnClickListener {
            sendChannel.safeOffer(TrailerIntent.OpenTrailerIntent(trailer))
        }
    }
}
