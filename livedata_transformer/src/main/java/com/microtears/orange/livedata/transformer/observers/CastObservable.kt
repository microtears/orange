package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class CastObservable<S, T> : ObserverBase<S, T>() {
    override fun onChanged(t: S) {
        @Suppress("UNCHECKED_CAST")
        val value = t as T
        setValue(value)
    }
}