@file:Suppress("NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.microtears.util.dp2px


inline fun Context.toast(charSequence: CharSequence) {
    Toast.makeText(applicationContext, charSequence, Toast.LENGTH_SHORT).show()
}

inline fun Fragment.toast(charSequence: CharSequence) {
    context!!.toast(charSequence)
}

inline fun Context.longToast(charSequence: CharSequence) {
    Toast.makeText(applicationContext, charSequence, Toast.LENGTH_SHORT).show()
}

inline fun Fragment.longToast(charSequence: CharSequence) {
    context!!.longToast(charSequence)
}

fun Context.toast2(charSequence: CharSequence) {
    Toast(applicationContext).also {
        it.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, dp2px(90))
        val view = inflate(R.layout.toast2_layout).apply {
            findViewById<TextView>(R.id.toastTextView).text = charSequence
        }
        it.view = view
        it.duration = Toast.LENGTH_SHORT
        try {
            it.show()
        } catch (e: Exception) {
            toast(charSequence)
        }
    }
}

inline fun Fragment.toast2(charSequence: CharSequence) {
    context!!.toast2(charSequence)
}