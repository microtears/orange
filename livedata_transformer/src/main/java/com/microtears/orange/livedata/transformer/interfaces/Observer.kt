package com.microtears.orange.livedata.transformer.interfaces

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.ObserverHelper

abstract class Observer<S, T>(val sync: Boolean = true) :
    ObserverInternal<S, T> {

    internal lateinit var helper: ObserverHelper<S, T>

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