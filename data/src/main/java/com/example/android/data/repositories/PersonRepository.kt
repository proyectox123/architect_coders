package com.example.android.data.repositories

import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.Person
import com.example.android.framework.data.remote.requests.Result

class PersonRepository(
    private val remotePersonDataSource: RemotePersonDataSource
){

    //region Public Methods

    suspend fun getPerson(movieId: Int): Result<Person> =
        remotePersonDataSource.getPerson(movieId)

    //endregion

}