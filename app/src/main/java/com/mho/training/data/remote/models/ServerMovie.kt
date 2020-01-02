package com.mho.training.data.remote.models

data class ServerMovie(val id: Int,
                       val title: String,
                       val releaseDate: String,
                       val posterPath: String,
                       val voteAverage: Double,
                       val plotSynopsis: String)