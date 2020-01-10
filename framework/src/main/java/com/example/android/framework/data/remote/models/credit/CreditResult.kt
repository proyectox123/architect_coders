package com.example.android.framework.data.remote.models.credit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CreditResult(
    val id: Int,
    @SerializedName("cast") val results: List<ServerCredit>
)