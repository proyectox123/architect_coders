package com.mho.training.data.translators

import com.example.android.domain.Person
import com.example.android.frameworkretrofit.data.models.person.ServerPerson
import com.mho.training.utils.Constants
import com.mho.training.utils.Constants.DATE_FORMAT_PATTERN_INPUT
import com.mho.training.utils.Constants.DATE_FORMAT_PATTERN_OUTPUT
import com.mho.training.utils.generateDateFormat


fun ServerPerson.toDomainPerson(): Person =
    Person(
        id,
        name,
        knownForDepartment,
        biography,
        generatePersonProfilePath(profilePath),
        generatePersonBirthdayInformation(birthday,placeOfBirth),
        deathday
    )

private fun generatePersonProfilePath(profilePath: String?) =
    if(profilePath.isNullOrBlank()) null else Constants.URL_IMAGE_TBMD + profilePath

private fun generatePersonBirthdayInformation(birthday: String?, placeOfBirth: String?) =
    if(birthday.isNullOrBlank() || placeOfBirth.isNullOrBlank()){
        null
    } else {
        birthday.generateDateFormat(DATE_FORMAT_PATTERN_INPUT, DATE_FORMAT_PATTERN_OUTPUT) + "\n" + placeOfBirth
    }