package com.mho.training.data.remote.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ServerTrailer(val id: String,
                         val name: String,
                         val thumbnail: String,
                         val videoPath: String)