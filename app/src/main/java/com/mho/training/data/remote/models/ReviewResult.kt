package com.mho.training.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ReviewResult(
    val id: Int,
    val page: Int,
    val results: List<ServerReview>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

@Parcelize
data class ServerReview(
    @SerializedName("id") val id: String,
    val author: String,
    val content: String,
    val url: String
) : Parcelable