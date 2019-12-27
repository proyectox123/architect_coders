package com.mho.training.data.database.converters

import androidx.room.TypeConverter
import com.mho.training.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(timestamp: String): Date? {
            try {
                return SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT, Locale.US).parse(timestamp)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return Date()
        }

        @TypeConverter
        @JvmStatic
        fun toTimestamp(date: Date): String {
            return SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT, Locale.US).format(date)
        }
    }
}