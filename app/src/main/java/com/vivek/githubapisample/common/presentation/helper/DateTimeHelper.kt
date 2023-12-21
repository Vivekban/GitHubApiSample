package com.vivek.githubapisample.common.presentation.helper

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * A collection of utility functions for working with dates and times.
 */
object DateTimeUtils {

    /** The date format used to parse the input string. */
    private const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    /** Date only format used to display date to user **/
    private const val DATE_ONLY_FORMAT_LOCAL = "MMM dd, yyyy"

    /**
     * Gets the display date only from the server date.
     *
     * Note: Return null if the input string is null or the parsing fails.
     *
     * @param input The input string in the format "yyyy-MM-dd'T'HH:mm:ss'Z'".
     * @return The date only in the format "MMM dd, yyyy".
     */
    fun getOnlyDate(input: String?): String? {
        // Check if the input string is null.
        input?.let {
            // Create a SimpleDateFormat object with the specified date format.
            val parser = SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault())
            try {
                // Parse the input string into a Date object.
                parser.parse(it)?.let { date ->

                    // Create a SimpleDateFormat object with the desired date format.
                    return SimpleDateFormat(
                        DATE_ONLY_FORMAT_LOCAL,
                        Locale.getDefault()
                    ).format(date)

                }
            } catch (e: ParseException) {
                // TODO: handle date parsing exception
                Log.d("TAG", "getDayWithMonthName: $e")
            }
        }
        // Return null if the input string is null or the parsing fails.
        return null
    }
}