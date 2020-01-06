package com.example.android.framework.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TrailerResult(
    val id: Int,
    val results: List<ServerTrailer>
)

@Parcelize
data class ServerTrailer(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String
) : Parcelable