package com.microtears.orange.util

import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.forEachIndexed
import androidx.core.view.get

/**
 * from anko
 * Return the first child [View] matching the given [predicate].
 *
 * @param predicate the predicate to check against.
 * @return the child [View] that matches [predicate].
 *   [NoSuchElementException] will be thrown if no such child was found.
 */
inline fun ViewGroup.firstChild(predicate: (View) -> Boolean): View {
    return firstChildOrNull(predicate)
        ?: throw NoSuchElementException("No element matching predicate was found.")
}

/**
 * from anko
 * Return the first child [View] matching the given [predicate].
 *
 * @param predicate the predicate to check against.
 * @return the child [View] that matches [predicate], or null if no such child was found.
 */
inline fun ViewGroup.firstChildOrNull(predicate: (View) -> Boolean): View? {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (predicate(child)) {
            return child
        }
    }
    return null
}

inline val ViewGroup.first: View
    get() = firstOrNull ?: throw NoSuchElementException("No first view.")

inline val ViewGroup.firstOrNull: View?
    get() = this.getChildAt(0)

inline val ViewGroup.last: View
    get() = lastOrNull ?: throw NoSuchElementException("No last view.")

inline val ViewGroup.lastOrNull: View?
    get() = if (childCount >= 1) this.getChildAt(childCount - 1) else null

inline fun ViewGroup.foreach(block: (View) -> Unit) {
    for (i in 0 until childCount) {
        block(get(i))
    }
}

/*****************************************/
val RadioGroup.checkedIndex: Int?
    get() {
        forEachIndexed { index, v -> takeIf { v.id == checkedRadioButtonId }?.apply { return index } }
        return null
    }