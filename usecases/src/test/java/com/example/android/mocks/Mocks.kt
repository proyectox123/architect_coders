package com.example.android.mocks

import com.example.android.domain.*

internal val mockedMovie = Movie(
    0,
    "Title",
    "01/01/2025",
    "",
    5.0,
    "Average: 5/5",
    "Synopsis"
)

internal val mockedMovieDetail = MovieDetail(
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

internal val mockedCredit = Credit(
    0,
    "Credit",
    "Character",
    ""
)

internal val mockedKeyword = Keyword(
    0,
    "name"
)

internal val mockedPerson = Person(
    0,
    "name",
    "knownForDepartment",
    "biography",
    "",
    "",
    ""
)

internal val mockedReview = Review(
    "0",
    "author",
    "content",
    ""
)

internal val mockedTrailer = Trailer(
    "0",
    "name",
    "",
    ""
)