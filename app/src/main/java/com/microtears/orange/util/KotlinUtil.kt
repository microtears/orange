@file:Suppress("NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

//fun <R> getCache(cache: MutableMap<String, Any>, key: String, default: () -> R): R {
//    var r = cache[key]
//    if (r == null) {
//        val value = default()
//        r = value
//        cache[key] = value as Any
//    }
//    return r as R
//}

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String = "yyyy-MM-dd"): Date {
    return SimpleDateFormat(pattern).parse(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toFormatString(pattern: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}