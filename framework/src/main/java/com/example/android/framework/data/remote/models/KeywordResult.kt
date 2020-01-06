package com.example.android.framework.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class KeywordResult(
    val id: Int,
    @SerializedName("keywords") val results: List<ServerKeyword>
)

@Parcelize
data class ServerKeyword(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable