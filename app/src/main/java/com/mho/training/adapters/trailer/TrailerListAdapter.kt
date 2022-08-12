package com.mho.training.adapters.trailer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Trailer
import com.mho.training.R
import com.mho.training.features.trailers.mvi.TrailerIntent
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate
import kotlinx.coroutines.channels.SendChannel

class TrailerListAdapter(
    private val sendChannel: SendChannel<TrailerIntent.OpenTrailerIntent>,
) :
    RecyclerView.Adapter<TrailerViewHolder>() {

    var trailers: List<Trailer> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrailerViewHolder(parent.bindingInflate(R.layout.item_trailer, false), sendChannel)

    override fun getItemCount() = trailers.size

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(trailers[position])
    }
}