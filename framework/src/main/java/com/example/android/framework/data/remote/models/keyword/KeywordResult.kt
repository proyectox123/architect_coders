package com.example.android.framework.data.remote.models.keyword

import com.google.gson.annotations.SerializedName

data class KeywordResult(
    val id: Int,
    @SerializedName("keywords") val results: List<ServerKeyword>
)