package com.nqproject.MoneyApp.utils

import java.time.Instant
import java.time.format.DateTimeFormatter.*
import java.util.*


object DateUtils {

    fun parseDate(str: String): Date {
        val ta = ISO_INSTANT.parse(str)
        val i = Instant.from(ta)
        return Date.from(i)
    }

}