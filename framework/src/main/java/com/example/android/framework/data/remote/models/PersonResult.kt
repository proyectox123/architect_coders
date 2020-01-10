package com.example.android.framework.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerPerson(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("biography") val biography: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("deathday") val deathday: String
) : Parcelable