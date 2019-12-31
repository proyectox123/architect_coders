package com.mho.training.data.remote.parsers

import com.mho.training.data.remote.models.ServerTrailer
import org.json.JSONException
import org.json.JSONObject


object TrailerListJsonUtils {

    private val URL_THUMBNAIL_YOUTUBE_VIDEO = "http://img.youtube.com/vi/"
    private val URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT = "/hqdefault.jpg"
    private val URL_YOUTUBE_VIDEO = "https://www.youtube.com/watch?v="

    private val JSON_ARRAY_RESULTS = "results"
    private val JSON_OBJECT_ID = "id"
    private val JSON_OBJECT_KEY = "key"
    private val JSON_OBJECT_NAME = "name"

    @Throws(JSONException::class)
    fun getTrailerListFromJson(listJsonStr: String): List<ServerTrailer> {
        val listJson = JSONObject(listJsonStr)
        val resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS)

        val trailerList = mutableListOf<ServerTrailer>()
        for (i in 0 until resultJsonArray.length()) {
            val trailerFromJsonObject = resultJsonArray.getJSONObject(i)
            trailerList.add(getTrailerFromJsonObject(trailerFromJsonObject))
        }

        return trailerList
    }

    @Throws(JSONException::class)
    private fun getTrailerFromJsonObject(jsonObject: JSONObject): ServerTrailer {
        val id = jsonObject.getString(JSON_OBJECT_ID)
        val name = jsonObject.getString(JSON_OBJECT_NAME)

        val key = jsonObject.getString(JSON_OBJECT_KEY)
        val videoThumbnail =
            URL_THUMBNAIL_YOUTUBE_VIDEO + key + URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT
        val videoPath = URL_YOUTUBE_VIDEO + key

        return ServerTrailer(id, name, videoThumbnail, videoPath)
    }
}