@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.annotation.Px

inline fun View.setHeight(height: Int) {
    setSize(width, height)
}

inline fun View.setWidth(width: Int) {
    setSize(width, height)
}

fun View.setSize(width: Int, height: Int) {
    var layoutParams = this.layoutParams
    if (layoutParams == null) {
        layoutParams = ViewGroup.LayoutParams(width, height)
    } else {
        layoutParams.width = width
        layoutParams.height = height
    }
    this.layoutParams = layoutParams
}

inline fun View.visible() {
    visibility = View.VISIBLE
}

inline fun View.gone() {
    visibility = View.GONE
}

inline fun View.inVisible() {
    visibility = View.INVISIBLE
}

inline fun View.enable() {
    isEnabled = true
}

inline fun View.unEnable() {
    isEnabled = false
}

inline fun CompoundButton.checked() {
    isChecked = true
}

inline fun CompoundButton.unChecked() {
    isChecked = false
}


//inline fun View.layoutLeft(@Px left: Int) {
//    layout(left, top, left + right - this.left, bottom)
//}
//
//inline fun View.layoutRight(@Px right: Int) {
//    layout(right - this.right - left, top, right, bottom)
//}
//
//inline fun View.layoutTop(@Px top: Int) {
//    layout(left, top, right, top + bottom - this.top)
//}
//
//inline fun View.layoutBottom(@Px bottom: Int) {
//    layout(left, bottom - this.bottom - top, right, bottom)
//}

//inline fun View.setMargins(@Px marginLeft: Int, @Px marginTop: Int, @Px marginRight: Int, @Px marginBottom: Int) {
//    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
//}
//
//inline fun View.setMarginLeft(@Px marginLeft: Int) {
//    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
//}
//
//inline fun View.setMarginTop(@Px marginTop: Int) {
//    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
//}
//
//inline fun View.setMarginRight(@Px marginRight: Int) {
//    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
//}
//
//inline fun View.setMarginBottom(@Px marginBottom: Int) {
//    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
//}

inline fun View.setPaddingLeft(@Px paddingLeft: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

inline fun View.setPaddingTop(@Px paddingTop: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

inline fun View.setPaddingRight(@Px paddingRight: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

inline fun View.setPaddingBottom(@Px paddingBottom: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

inline fun View?.removeInParent() {
    (this?.parent as? ViewGroup)?.removeView(this)
}

inline val View?.parentView: ViewGroup?
    get() {
        return (this?.parent as? ViewGroup)
    }

fun View.openKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.closeKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}