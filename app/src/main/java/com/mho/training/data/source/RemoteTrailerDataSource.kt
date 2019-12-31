package com.mho.training.data.source

import com.mho.training.domain.Trailer

interface RemoteTrailerDataSource {
    suspend fun getTrailerList(movieId: Int): List<Trailer>
}