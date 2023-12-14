package com.vivek.githubapisample.ui.common

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat

object DateTimeUtils {

    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @SuppressLint("SimpleDateFormat")
    fun getDayWithMonthName(input: String?): String? {
        input?.let {
            val parser = SimpleDateFormat(DATE_FORMAT)
            try {
                parser.parse(it)?.let { date ->

                    return SimpleDateFormat("MMM dd yyyy").format(date)

                }
            } catch (e: ParseException) {
                Log.d("TAG", "getDayWithMonthName: $e")
            }
        }
        return null
    }
}