package com.mho.training.data.database.tables

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mho.training.R
import com.mho.training.utils.Constants.SIMPLE_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") var id: Int,
    @ColumnInfo(name = "movie_title") var title: String,
    @ColumnInfo(name = "movie_release_date") var releaseDate: Date,
    @ColumnInfo(name = "movie_poster_path") var posterPath: String,
    @ColumnInfo(name = "movie_vote_average") var voteAverage: Double,
    @ColumnInfo(name = "movie_plot_synopsis") var plotSynopsis: String
){
    constructor(): this(0, "", Date(), "", 0.0, "")

    val releaseDateLabel: String
        get() {
            val outFormatDate = SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US)
            return outFormatDate.format(releaseDate)
        }

    fun getVoteAverageLabel(context: Context): String {
        val voteAverageLabel = "$voteAverage/10"
        return context.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
    }
}