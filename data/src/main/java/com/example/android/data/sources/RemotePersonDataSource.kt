package com.example.android.data.sources

import com.example.android.domain.Person
import com.example.android.framework.data.remote.requests.Result

interface RemotePersonDataSource {
    suspend fun getPerson(personId: Int): Result<Person>
}