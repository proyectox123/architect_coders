package com.example.android.testshared

import com.example.android.domain.*
import com.example.android.domain.user.LogInParams

val mockedMovie = Movie(
    0,
    "Title",
    "01/01/2025",
    "",
    5.0,
    "Average: 5/5",
    "Synopsis"
)

val mockedMovieDetail = MovieDetail(
    0,
    "title",
    "00:00",
    listOf(
        Genre(
            0,
            "name"
        )
    )
)

val mockedCredit = Credit(
    0,
    "Credit",
    "Character",
    ""
)

val mockedKeyword = Keyword(
    0,
    "name"
)

val mockedPerson = Person(
    0,
    "name",
    "knownForDepartment",
    "biography",
    "",
    "",
    ""
)

val mockedReview = Review(
    "0",
    "author",
    "content",
    ""
)

val mockedTrailer = Trailer(
    "0",
    "name",
    "",
    ""
)

val mockedLogInParams = LogInParams(
        "",
        ""
)