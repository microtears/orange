package com.microtears.orange.livedata2

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class ObserverBase<S, T>(val sync: Boolean = true) : Observer<S> {

    open fun setError(throwable: Throwable){
        throw NotImplementedError()
    }

    open fun setValue(value: T) {
        throw  NotImplementedError()
    }

    open fun getValue(): T? {
        throw  NotImplementedError()
    }

    open fun getSource(): LiveData<S> {
        throw  NotImplementedError()
    }

    open fun addSource(source: LiveData<T>, observer: (T) -> Unit) {
        throw  NotImplementedError()
    }

    open fun removeSource(source: LiveData<T>) {
        throw  NotImplementedError()
    }
}