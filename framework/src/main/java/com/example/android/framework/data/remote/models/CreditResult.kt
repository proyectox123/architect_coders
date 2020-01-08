package com.example.android.framework.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CreditResult(
    val id: Int,
    @SerializedName("cast") val results: List<ServerCredit>
)

@Parcelize
data class ServerCredit(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("character") val character: String,
    @SerializedName("profile_path") val profilePath: String
) : Parcelable