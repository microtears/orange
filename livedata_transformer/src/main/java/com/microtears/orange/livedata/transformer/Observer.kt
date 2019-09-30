package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface Observer<S, T> : Observer<S> {

    fun setError(throwable: Throwable)

    fun setValue(value: T)

    fun getValue(): T?

    fun getSource(): LiveData<S>

    fun addSource(source: LiveData<T>, observer: (T) -> Unit)

    fun removeSource(source: LiveData<T>)
}