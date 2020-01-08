package com.mho.training.adapters.credit

import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Credit
import com.mho.training.databinding.ItemCreditBinding

class CreditViewHolder(private val dataBinding: ItemCreditBinding,
                       private val listener: (Credit) -> Unit) :
    RecyclerView.ViewHolder(dataBinding.root){

    fun bind(credit: Credit){
        dataBinding.credit = credit
        itemView.setOnClickListener { listener(credit) }
    }

}