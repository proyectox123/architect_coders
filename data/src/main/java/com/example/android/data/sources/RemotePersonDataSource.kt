package com.example.android.data.sources

import com.example.android.domain.Person
import com.example.android.domain.result.DataResult

interface RemotePersonDataSource {
    suspend fun getPerson(personId: Int): DataResult<Person>
}