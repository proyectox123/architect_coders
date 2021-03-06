package com.example.android.frameworkretrofit.data.models.keyword

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerKeyword(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable