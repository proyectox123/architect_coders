package com.mho.training.adapters.keyword

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Keyword
import com.mho.training.R
import com.mho.training.utils.basicDiffUtil
import com.mho.training.utils.bindingInflate

class KeywordListAdapter(private val listener: (Keyword) -> Unit) :
    RecyclerView.Adapter<KeywordViewHolder>() {

    var keywords: List<Keyword> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        KeywordViewHolder(parent.bindingInflate(R.layout.item_keyword, false), listener)

    override fun getItemCount() = keywords.size

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywords[position])
    }
}