package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.Person
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.framework.data.remote.models.person.ServerPerson
import com.example.android.framework.data.remote.requests.person.PersonRequest
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class PersonDataSource(private val resources: Resources) : RemotePersonDataSource {

    override suspend fun getPerson(personId: Int): DataResult<Person> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestPerson(personId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_person)
        )
    }

    private suspend fun requestPerson(movieId: Int): DataResult<Person> {
        val response: Response<ServerPerson> = PersonRequest.service
            .getPersonByCreditIdAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val result: ServerPerson? = response.body()
            if (result != null) {
                return DataResult.Success(result.toDomainPerson())
            }
        }

        return DataResult.Error(IOException(resources.getString(R.string.error_during_fetching_person)))
    }
}