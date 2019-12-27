package com.mho.training.adapters.trailer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mho.training.R
import com.mho.training.data.remote.models.Trailer
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class TrailerListAdapter(private val listener: (Trailer) -> Unit) :
    RecyclerView.Adapter<TrailerViewHolder>() {

    var trailers: List<Trailer> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrailerViewHolder(parent.bindingInflate(R.layout.item_trailer, false), listener)

    override fun getItemCount() = trailers.size

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(trailers[position])
    }
}