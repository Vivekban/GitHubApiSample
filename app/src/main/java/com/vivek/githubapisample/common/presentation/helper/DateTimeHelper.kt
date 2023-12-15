package com.vivek.githubapisample.common.presentation.helper

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat

object DateTimeUtils {

    /** The date format used to parse the input string. */
    private const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val DATE_ONLY_FORMAT_LOCAL = "MMM dd, yyyy"

    @SuppressLint("SimpleDateFormat")
    fun getOnlyDate(input: String?): String? {
        // Check if the input string is null.
        input?.let {
            // Create a SimpleDateFormat object with the specified date format.
            val parser = SimpleDateFormat(DATE_FORMAT_SERVER)
            try {
                // Parse the input string into a Date object.
                parser.parse(it)?.let { date ->

                    // Create a SimpleDateFormat object with the desired date format.
                    return SimpleDateFormat(DATE_ONLY_FORMAT_LOCAL).format(date)

                }
            } catch (e: ParseException) {
                // Log the exception if it occurs.
                Log.d("TAG", "getDayWithMonthName: $e")
            }
        }
        // Return null if the input string is null or the parsing fails.
        return null
    }
}