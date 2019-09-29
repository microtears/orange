package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class SkipObservable<S>(private val size: Int) : ObserverBase<S, S>() {
    private var count = 0
    override fun onChanged(t: S) {
        // Overflow errors may occur
        if (++count >= size)
            setValue(t)
    }
}