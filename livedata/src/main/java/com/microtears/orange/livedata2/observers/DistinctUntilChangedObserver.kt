package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class DistinctUntilChangedObserver<S> : ObserverBase<S, S>() {

    override fun onChanged(currentValue: S) {
        val previousValue = getValue()
        if (currentValue != previousValue) {
            setValue(currentValue)
        }
    }
}