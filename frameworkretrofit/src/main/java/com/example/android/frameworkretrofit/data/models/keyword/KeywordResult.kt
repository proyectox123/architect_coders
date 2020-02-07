package com.example.android.frameworkretrofit.data.models.keyword

import com.example.android.framework.data.remote.models.keyword.ServerKeyword
import com.google.gson.annotations.SerializedName

data class KeywordResult(
    val id: Int,
    @SerializedName("keywords") val results: List<ServerKeyword>
)