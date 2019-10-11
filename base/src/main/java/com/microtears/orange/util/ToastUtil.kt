@file:Suppress("NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


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