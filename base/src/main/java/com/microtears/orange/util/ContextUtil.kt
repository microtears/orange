@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/*
* color
* */
inline fun Context.color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

inline fun Fragment.color(@ColorRes id: Int) = ContextCompat.getColor(context!!, id)

/*
* string
* */
fun Context.string(@StringRes id: Int, vararg args: Any?): String {
    return if (args.isEmpty()) getString(id) else getString(id, *args)
}

fun Fragment.string(@StringRes id: Int, vararg args: Any?): String {
    return if (args.isEmpty()) getString(id) else getString(id, *args)
}

/*
* inflate
* */
inline fun Context.inflate(res: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false) =
    LayoutInflater.from(this).inflate(res, parent, attachToRoot)!!

inline fun Fragment.inflate(res: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(res, parent, attachToRoot)!!

/*
* uri to path
* */
inline fun Context.getFilePathByUri(uri: Uri): String {
    return getFilePathByUri(this, uri)
}

inline fun Fragment.getFilePathByUri(uri: Uri): String {
    return getFilePathByUri(context!!, uri)
}

/*
* isDestroy
* */
fun Context?.isDestroy(): Boolean {
    if (this == null)
        return true
    return when (this) {
        is FragmentActivity -> {
            this.isDestroyed
        }
        is Activity -> {
            this.isDestroyed
        }
        is Application -> {
            false
        }
        else -> {
            true
        }
    }
}

/*
* displayMetrics
* */
inline val Context.displayMetrics: DisplayMetrics
    get() = resources.displayMetrics

inline val Fragment.displayMetrics: DisplayMetrics
    get() = resources.displayMetrics

/*
* displayWidth
* */
inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

inline val Fragment.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

/*
* displayHeight
* */
inline val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

inline val Fragment.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

/*
* statusBarHeight
* */
val Context.statusBarHeight: Int
    get() {
        val id = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (id > 0) resources.getDimensionPixelSize(id) else dp2px(25)
    }

val Fragment.statusBarHeight: Int
    get() {
        val id = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (id > 0) resources.getDimensionPixelSize(id) else dp2px(25)
    }

/*
* navigationBarHeight
* */
val Context.navigationBarHeight: Int
    get() {
        val id = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (id > 0) resources.getDimensionPixelSize(id) else dp2px(25)
    }

val Fragment.navigationBarHeight: Int
    get() {
        val id = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (id > 0) resources.getDimensionPixelSize(id) else dp2px(25)
    }

inline val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

inline val Fragment.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

inline val Context.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

inline val Fragment.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
