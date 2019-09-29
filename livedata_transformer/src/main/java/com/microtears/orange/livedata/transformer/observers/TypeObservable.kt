package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class TypeObservable<S, T>(private val clazz: Class<T>) : ObserverBase<S, T>() {
    override fun onChanged(t: S) {
        if (clazz.isInstance(t)) {
            @Suppress("UNCHECKED_CAST") val value = t as T
            setValue(value)
        }
    }
}