package com.example.android.framework.data.remote.models.credit

import com.google.gson.annotations.SerializedName

data class CreditResult(
    val id: Int,
    @SerializedName("cast") val results: List<ServerCredit>
)