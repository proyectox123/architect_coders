package com.example.android.usecases

import com.example.android.data.repositories.PersonRepository
import com.example.android.domain.Person
import com.example.android.framework.data.remote.requests.Result

class GetPersonInformationUseCase(private val personRepository: PersonRepository) {

    suspend fun invoke(personId: Int): Result<Person> =
        personRepository.getPerson(personId)

}