package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class AtObservable<S>(private val index: Int) : ObserverBase<S, S>() {
    private var count = -1
    override fun onChanged(t: S) {
        if (++count == index)
            setValue(t)
    }
}