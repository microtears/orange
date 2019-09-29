package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class ObserverWrap<S, T>(
    private val observer: ObserverBase<S, T>,
    private val setFunction: (T) -> Unit,
    private val getFunction: () -> T?,
    private val postFunction: (T) -> Unit,
    private val addSourceFunc: (LiveData<T>, Observer<in T>) -> Unit,
    private val removeSourceFunc: (LiveData<T>) -> Unit,
    private val getSourceFunc: () -> LiveData<S>
) :
    ObserverBase<S, T>(observer.sync) {

    override fun onChanged(t: S) {
        try {
            if (t is ValueOr<*>) {
                if (t.hasError()) {
                    throw t.throwable!!
                } else {
                    if (t.hasValue()) {
                        @Suppress("UNCHECKED_CAST")
                        observer.onChanged(t.value!! as S)
                    }
                }
            } else {
                observer.onChanged(t)
            }
        } catch (throwable: Throwable) {
            setError(throwable)
        }
    }

    override fun setError(throwable: Throwable) {
        @Suppress("UNCHECKED_CAST")
        setValue(ValueOr.fromThrowable<T>(throwable) as T)
    }

    override fun setValue(value: T) {
        if (sync) {
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