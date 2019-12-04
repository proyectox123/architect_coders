package com.mho.training.data.database.converters

import androidx.room.TypeConverter

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.mho.training.utils.Constants.SIMPLE_DATE_FORMAT

object DateConverter {
    @TypeConverter
    fun toDate(timestamp: String): Date? {
        try {
            return SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US).parse(timestamp)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return Date()
    }

    @TypeConverter
    fun toTimestamp(date: Date): String {
        return SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US).format(date)
    }
}