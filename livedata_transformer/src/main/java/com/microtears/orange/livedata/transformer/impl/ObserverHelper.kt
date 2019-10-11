package com.microtears.orange.livedata.transformer.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.microtears.orange.livedata.transformer.interfaces.ObserverInternal

internal class ObserverHelper<S, T>(
    private val observer: com.microtears.orange.livedata.transformer.interfaces.Observer<S, T>,
    private val setFunction: (T) -> Unit,
    private val getFunction: () -> T?,
    private val postFunction: (T) -> Unit,
    private val addSourceFunc: (LiveData<T>, Observer<in T>) -> Unit,
    private val removeSourceFunc: (LiveData<T>) -> Unit,
    private val getSourceFunc: () -> LiveData<S>
) : ObserverInternal<S, T> {

    override fun onChanged(t: S) {
        observer.onChanged(t)
    }


    override fun setValue(value: T) {
        if (observer.sync) {
            setFunction(value)
        } else {
            postFunction(value)
        }
    }

    override fun getValue(): T? {
        return getFunction()
    }

    override fun getSource(): LiveData<S> {
        return getSourceFunc()
    }

    override fun addSource(source: LiveData<T>, observer: (T) -> Unit) {
        addSourceFunc(source, Observer { t -> observer(t) })
    }

    override fun removeSource(source: LiveData<T>) {
        removeSourceFunc(source)
    }

}