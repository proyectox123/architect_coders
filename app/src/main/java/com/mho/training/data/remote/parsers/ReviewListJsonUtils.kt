package com.mho.training.data.remote.parsers

import com.mho.training.data.remote.models.ServerReview
import org.json.JSONException
import org.json.JSONObject


object ReviewListJsonUtils {

    private val JSON_ARRAY_RESULTS = "results"
    private val JSON_OBJECT_ID = "id"
    private val JSON_OBJECT_AUTHOR = "author"
    private val JSON_OBJECT_CONTENT = "content"
    private val JSON_OBJECT_URL = "url"

    @Throws(JSONException::class)
    fun getReviewListFromJson(listJsonStr: String): List<ServerReview> {
        val listJson = JSONObject(listJsonStr)
        val resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS)

        val reviewList = mutableListOf<ServerReview>()
        for (i in 0 until resultJsonArray.length()) {
            val reviewFromJsonObject = resultJsonArray.getJSONObject(i)
            reviewList.add(getReviewFromJsonObject(reviewFromJsonObject))
        }

        return reviewList
    }

    @Throws(JSONException::class)
    private fun getReviewFromJsonObject(jsonObject: JSONObject): ServerReview {
        val id = jsonObject.getString(JSON_OBJECT_ID)
        val author = jsonObject.getString(JSON_OBJECT_AUTHOR)
        val content = jsonObject.getString(JSON_OBJECT_CONTENT)
        val url = jsonObject.getString(JSON_OBJECT_URL)

        return ServerReview(id, author, content, url)
    }
}