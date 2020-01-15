package com.example.android.data.repositories

import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.Person
import com.example.android.domain.result.DataResult

class PersonRepository(
    private val remotePersonDataSource: RemotePersonDataSource
){

    //region Public Methods

    suspend fun getPerson(movieId: Int): DataResult<Person> =
        remotePersonDataSource.getPerson(movieId)

    //endregion

}