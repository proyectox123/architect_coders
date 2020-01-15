package com.example.android.framework.data.remote.models.genre

import com.google.gson.annotations.SerializedName

data class ServerGenre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)