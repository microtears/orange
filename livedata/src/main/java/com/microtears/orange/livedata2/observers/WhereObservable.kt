package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class WhereObservable<S>(private val test: (S) -> Boolean) : ObserverBase<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            setValue(t)
    }
}