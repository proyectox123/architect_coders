package com.example.android.data.repositories

import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.Trailer
import com.example.android.framework.data.remote.requests.Result

class TrailerRepository(
    private val remoteTrailerDataSource: RemoteTrailerDataSource
){

    //region Public Methods

    suspend fun getTrailerList(movieId: Int): Result<List<Trailer>> =
        remoteTrailerDataSource.getTrailerList(movieId)

    //endregion

}