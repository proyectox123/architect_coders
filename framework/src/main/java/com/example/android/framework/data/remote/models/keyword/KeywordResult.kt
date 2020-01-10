package com.example.android.framework.data.remote.models.keyword

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class KeywordResult(
    val id: Int,
    @SerializedName("keywords") val results: List<ServerKeyword>
)