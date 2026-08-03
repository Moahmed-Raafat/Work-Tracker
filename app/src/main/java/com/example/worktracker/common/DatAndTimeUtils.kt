package com.example.worktracker.common

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateTime(dateTime: String): String {

    if(dateTime == "") return ""

    val instant = Instant.parse(dateTime)

    val formatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy  hh:mm a",
        Locale.getDefault()
    )

    return instant
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}