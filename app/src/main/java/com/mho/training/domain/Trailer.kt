package com.mho.training.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trailer(val id: String,
                         val name: String,
                         val thumbnail: String,
                         val videoPath: String): Parcelable