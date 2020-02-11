package com.example.android.frameworkretrofit.data.models.keyword

import com.google.gson.annotations.SerializedName

data class KeywordResult(
    val id: Int,
    @SerializedName("keywords") val results: List<ServerKeyword>
)