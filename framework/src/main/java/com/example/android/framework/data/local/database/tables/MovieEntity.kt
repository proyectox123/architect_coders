package com.example.android.framework.data.local.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") var id: Int,
    @ColumnInfo(name = "movie_title") var title: String,
    @ColumnInfo(name = "movie_release_date") var releaseDate: String,
    @ColumnInfo(name = "movie_poster_path") var posterPath: String,
    @ColumnInfo(name = "movie_vote_average") var voteAverage: Double,
    @ColumnInfo(name = "movie_plot_synopsis") var plotSynopsis: String
)