package com.example.android.frameworkretrofit.data.models.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerReview(
    @SerializedName("id") val id: String,
    val author: String,
    val content: String,
    val url: String
) : Parcelable