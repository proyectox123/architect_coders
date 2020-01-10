package com.example.android.framework.data.remote.models.review

import com.google.gson.annotations.SerializedName

data class ReviewResult(
    val id: Int,
    val page: Int,
    val results: List<ServerReview>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)