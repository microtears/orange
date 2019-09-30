package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData

abstract class ObserverBase<S, T>(val sync: Boolean = true) : Observer<S, T> {

    internal lateinit var helper: ObserverHelper<S, T>

    override fun setError(throwable: Throwable) {
        helper.setError(throwable)
    }

    override fun setValue(value: T) {
        helper.setValue(value)
    }

    override fun getValue(): T? {
        return helper.getValue()
    }

    override fun getSource(): LiveData<S> {
        return helper.getSource()
    }

    override fun addSource(source: LiveData<T>, observer: (T) -> Unit) {
        helper.addSource(source, observer)
    }

    override fun removeSource(source: LiveData<T>) {
        helper.removeSource(source)
    }
}