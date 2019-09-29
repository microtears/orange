package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

open class ReduceObservable<S, R, T>(
    private var initValue: R,
    private var apply: (R, S) -> T
) : ObserverBase<S, T>() {
    override fun onChanged(t: S) {
        val value = apply(initValue, t)
        setValue(value)
    }
}