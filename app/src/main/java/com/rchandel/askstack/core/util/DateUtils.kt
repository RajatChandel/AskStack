package com.rchandel.askstack.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = Date(timestamp * 1000) // StackOverflow returns UNIX seconds
    return sdf.format(date)
}