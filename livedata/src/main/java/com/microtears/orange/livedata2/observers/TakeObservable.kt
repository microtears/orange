package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class TakeObservable<S>(private val size: Int) : ObserverBase<S, S>() {
    private var count = 0
    override fun onChanged(t: S) {
        if (++count < size)
            setValue(t)
    }
}