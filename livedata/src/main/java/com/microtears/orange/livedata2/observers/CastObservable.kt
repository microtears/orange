package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class CastObservable<S, T> : ObserverBase<S, T>() {
    override fun onChanged(t: S) {
        @Suppress("UNCHECKED_CAST")
        val value = t as T
        setValue(value)
    }
}