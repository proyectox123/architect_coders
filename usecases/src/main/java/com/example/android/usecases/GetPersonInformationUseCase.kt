package com.example.android.usecases

import com.example.android.data.repositories.PersonRepository
import com.example.android.domain.Person
import com.example.android.domain.result.DataResult

class GetPersonInformationUseCase(private val personRepository: PersonRepository) {

    suspend fun invoke(personId: Int): DataResult<Person> =
        personRepository.getPerson(personId)

}