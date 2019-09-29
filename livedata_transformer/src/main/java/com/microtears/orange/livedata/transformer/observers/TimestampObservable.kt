package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class TimestampObservable<S> : ObserverBase<S, Pair<S, Long>>() {
    override fun onChanged(t: S) {
        val value = t to System.currentTimeMillis()
        setValue(value)
    }
}