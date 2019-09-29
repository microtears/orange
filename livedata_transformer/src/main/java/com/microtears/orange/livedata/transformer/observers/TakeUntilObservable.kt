package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class TakeUntilObservable<S>(
    private val test: (S) -> Boolean
) : ObserverBase<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            removeSource(getSource())
        else
            setValue(t)
    }
}