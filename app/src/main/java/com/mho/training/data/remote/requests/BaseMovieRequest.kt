package com.mho.training.data.remote.requests

import android.net.Uri
import android.util.Log

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Scanner

abstract class BaseMovieRequest {

    protected abstract fun createBuiltUri(): Uri

    fun buildUrl(): URL? {
        var url: URL? = null
        try {
            url = URL(createBuiltUri().toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Log.v(TAG, "Built URI " + url!!)

        return url
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }

    companion object {

        private val TAG = BaseMovieRequest::class.java.simpleName

        val QUERY_PARAMETER_API_KEY = "api_key"
        val BASE_MOVIE_DB_URL = "https://api.themoviedb.org/3/"
    }

}