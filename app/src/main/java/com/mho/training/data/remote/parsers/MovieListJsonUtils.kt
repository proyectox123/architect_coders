package com.mho.training.data.remote.parsers

import com.mho.training.data.remote.models.ServerMovie
import org.json.JSONException
import org.json.JSONObject
import java.util.*

object MovieListJsonUtils {

    //region Fields

    private val URL_IMAGE_TBMD = "http://image.tmdb.org/t/p/w185/"

    private val JSON_ARRAY_RESULTS = "results"
    private val JSON_OBJECT_ID = "id"
    private val JSON_OBJECT_TITLE = "title"
    private val JSON_OBJECT_RELEASE_DATE = "release_date"
    private val JSON_OBJECT_POSTER_PATH = "poster_path"
    private val JSON_OBJECT_VOTE_AVERAGE = "vote_average"
    private val JSON_OBJECT_OVERVIEW = "overview"

    //endregion

    //region Public Methods

    @Throws(JSONException::class)
    fun getMovieListFromJson(listJsonStr: String): List<ServerMovie> {
        val listJson = JSONObject(listJsonStr)
        val resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS)

        return ArrayList<ServerMovie>().apply {
            (0 until resultJsonArray.length())
                .asSequence()
                .map { resultJsonArray.getJSONObject(it) }
                .forEach { add(getMovieFromJsonObject(it)) }
        }
    }

    //endregion

    //region Private Methods

    @Throws(JSONException::class)
    private fun getMovieFromJsonObject(jsonObject: JSONObject): ServerMovie {
        val id = jsonObject.getInt(JSON_OBJECT_ID)
        val title = jsonObject.getString(JSON_OBJECT_TITLE)
        val releaseDate = jsonObject.getString(JSON_OBJECT_RELEASE_DATE)
        val posterPath = URL_IMAGE_TBMD + jsonObject.getString(JSON_OBJECT_POSTER_PATH)
        val voteAverage = jsonObject.getDouble(JSON_OBJECT_VOTE_AVERAGE)
        val plotSynopsis = jsonObject.getString(JSON_OBJECT_OVERVIEW)

        return ServerMovie(id, title, releaseDate, posterPath, voteAverage, plotSynopsis)
    }

    //endregion
}