package com.oli.weatherforecast.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun epochToSimpleTime(time: Long): String {
        val format = "h:mm a"
        var sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(time * 1000))
    }

    fun epochToShortTime(time: Long): String {
        val format = "h a"
        var sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(time * 1000))
    }

    fun epochToDay(time: Long): String {
        val format = "EEEE"
        var sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(time * 1000).time)

    }


}