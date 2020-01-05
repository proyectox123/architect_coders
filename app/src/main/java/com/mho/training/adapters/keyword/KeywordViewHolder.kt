package com.mho.training.adapters.keyword

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Keyword
import com.mho.training.databinding.ItemKeywordBinding

class KeywordViewHolder(private val dataBinding: ItemKeywordBinding,
                       private val listener: (Keyword) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(keyword: Keyword){
        dataBinding.keyword = keyword
        itemView.setOnClickListener { listener(keyword) }
    }

}