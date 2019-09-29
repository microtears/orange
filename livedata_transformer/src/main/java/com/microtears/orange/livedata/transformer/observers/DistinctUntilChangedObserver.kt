package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class DistinctUntilChangedObserver<S> : ObserverBase<S, S>() {

    override fun onChanged(currentValue: S) {
        val previousValue = getValue()
        if (currentValue != previousValue) {
            setValue(currentValue)
        }
    }
}