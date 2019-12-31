package com.mho.training.data

import com.mho.training.data.source.RemoteTrailerDataSource
import com.mho.training.domain.Trailer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrailerRepository(
    private val remoteTrailerDataSource: RemoteTrailerDataSource
){

    //region Public Methods

    suspend fun getTrailerList(movieId: Int): List<Trailer> = withContext(Dispatchers.IO) {
        remoteTrailerDataSource.getTrailerList(movieId)
    }

    //endregion

}