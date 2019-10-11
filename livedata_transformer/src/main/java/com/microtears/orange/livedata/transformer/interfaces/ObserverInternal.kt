package com.microtears.orange.livedata.transformer.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal interface ObserverInternal<S, T> : Observer<S> {

    fun setValue(value: T)

    fun getValue(): T?

    fun getSource(): LiveData<S>

    fun addSource(source: LiveData<T>, observer: (T) -> Unit)

    fun removeSource(source: LiveData<T>)
}