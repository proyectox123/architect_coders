package com.example.android.data.sources

import com.example.android.domain.Trailer

interface RemoteTrailerDataSource {
    suspend fun getTrailerList(movieId: Int): List<Trailer>
}