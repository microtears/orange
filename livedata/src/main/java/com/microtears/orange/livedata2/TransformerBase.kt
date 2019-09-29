package com.microtears.orange.livedata2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class TransformerBase<S, T>(private val observer: ObserverBase<S, T>) : Transformer<S, T> {
    override fun transform(source: LiveData<S>): LiveData<T> {
        val result = MediatorLiveData<T>()
        result.addSource(
            source, ObserverWrap(
                observer,
                result::setValue,
                result::getValue,
                result::postValue,
                result::addSource,
                result::removeSource
            ) { source }
        )
        return result;
    }
}