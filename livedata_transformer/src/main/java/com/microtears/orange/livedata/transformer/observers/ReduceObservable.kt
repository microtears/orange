package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

open class ReduceObservable<S, T>(
    private var initValue: T,
    private var apply: (T, S) -> T
) : ObserverBase<S, T>() {
    override fun onChanged(t: S) {
        initValue = apply(initValue, t)
        setValue(initValue)
    }
}