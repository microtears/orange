package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class SkipObservable<S>(private val size: Int) : ObserverBase<S, S>() {
    private var count = 0
    override fun onChanged(t: S) {
        // Overflow errors may occur
        if (++count >= size)
            setValue(t)
    }
}