package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class WhereObservable<S>(private val test: (S) -> Boolean) : ObserverBase<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            setValue(t)
    }
}