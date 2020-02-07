package com.mho.training.sources

import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.Person
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.frameworkretrofit.data.models.person.ServerPerson
import com.example.android.frameworkretrofit.data.requests.person.PersonRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class PersonServerDataSource(
    private val errorUnableToFetchPerson: String,
    private val errorDuringFetchingPerson: String
) : RemotePersonDataSource {

    override suspend fun getPerson(personId: Int): DataResult<Person> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestPerson(personId) },
            errorMessage = errorUnableToFetchPerson
        )
    }

    private suspend fun requestPerson(personId: Int): DataResult<Person> {
        val response: Response<ServerPerson> = PersonRequest.service
            .getPersonByCreditIdAsync(personId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val result: ServerPerson? = response.body()
            if (result != null) {
                return DataResult.Success(result.toDomainPerson())
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingPerson))
    }
}