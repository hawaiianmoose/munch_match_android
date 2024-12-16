package com.hawaiianmoose.munchmatch.util

import kotlinx.datetime.Clock

object LastUpdatedConverter {

    fun convertTimestampToLastUpdatedString(lastUpdated: Long): String {
        var time = ""
        val timeDifference = Clock.System.now().epochSeconds - lastUpdated
        val minutes = timeDifference / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30

        time = if (months > 0) {
            "$months" + "m"
        } else if (days > 0) {
            "$days" + "d"
        } else if (hours > 0) {
            "$hours" + "hr"
        } else if (minutes > 0) {
            "$minutes" + "min"
        } else {
            return "Last updated: now"
        }

        return "Last updated: $time ago"
    }
}
