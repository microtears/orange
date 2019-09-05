@file:Suppress("NOTHING_TO_INLINE")

package com.microtears.orange.util

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class State(val value: Int) {
    companion object {
        val EXPANDED = State(0)
        val COLLAPSED = State(1)
        val IDLE = State(2)
    }
}

abstract class AppBarOnStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var current = State.IDLE

    override fun onOffsetChanged(appbar: AppBarLayout?, i: Int) {
        current = when {
            i == 0 -> {
                if (current != State.EXPANDED) onChanged(State.EXPANDED)
                State.EXPANDED
            }
            abs(i) < appbar!!.totalScrollRange -> {
                if (current != State.COLLAPSED) onChanged(State.COLLAPSED)
                State.COLLAPSED
            }
            else -> {
                if (current != State.IDLE) onChanged(State.IDLE)
                State.IDLE
            }
        }
    }

    abstract fun onChanged(state: State)
}

inline fun AppBarLayout.addOnStateChangeListener(listener: AppBarOnStateChangeListener) {
    addOnOffsetChangedListener(listener)
}

fun AppBarLayout.addOnStateChangeListener(block: (State) -> Unit) {
    addOnOffsetChangedListener(object : AppBarOnStateChangeListener() {
        override fun onChanged(state: State) {
            block(state)
        }
    })
}