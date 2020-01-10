package com.example.android.framework.data.remote.models.trailer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerTrailer(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String
) : Parcelable