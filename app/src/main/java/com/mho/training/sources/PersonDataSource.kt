package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.Person
import com.example.android.framework.data.remote.models.person.ServerPerson
import com.example.android.framework.data.remote.requests.Result
import com.example.android.framework.data.remote.requests.person.PersonRequest
import com.example.android.framework.data.remote.requests.safeApiCall
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class PersonDataSource(private val resources: Resources) : RemotePersonDataSource {

    override suspend fun getPerson(personId: Int): Result<Person> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestPerson(personId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_person)
        )
    }

    private suspend fun requestPerson(movieId: Int): Result<Person> {
        val response: Response<ServerPerson> = PersonRequest.service
            .getPersonByCreditIdAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val result: ServerPerson? = response.body()
            if (result != null) {
                return Result.Success(result.toDomainPerson())
            }
        }

        return Result.Error(IOException(resources.getString(R.string.error_during_fetching_person)))
    }
}