package com.example.android.domain

data class Person(
    val id: Int,
    val name: String,
    val knownForDepartment: String,
    val biography: String,
    val profilePath: String?,
    val birthdayInformation: String?,
    val deathday: String?
)