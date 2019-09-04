@file:Suppress("unused")

package com.microtears.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment

/*context activity*/
fun Context.dp2px(dp: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()

fun Context.px2dp(px: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()

fun Context.sp2px(sp: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics)

/*fragment*/
fun Fragment.dp2px(dp: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()

fun Fragment.px2dp(px: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()

fun Fragment.sp2px(sp: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics)

/*view*/
fun View.dp2px(dp: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()

fun View.px2dp(px: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()

fun View.sp2px(sp: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics)
