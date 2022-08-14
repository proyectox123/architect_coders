package com.mho.training.adapters.credit

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.domain.Credit
import com.mho.training.R
import com.mho.training.coreandroid.extensions.basicDiffUtil
import com.mho.training.coreandroid.extensions.bindingInflate

class CreditListAdapter(private val listener: (Credit) -> Unit) :
    RecyclerView.Adapter<CreditViewHolder>() {

    var credits: List<Credit> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = {old, new -> old.id == new.id}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CreditViewHolder(parent.bindingInflate(R.layout.item_credit, false), listener)

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        holder.bind(credits[position])
    }
}