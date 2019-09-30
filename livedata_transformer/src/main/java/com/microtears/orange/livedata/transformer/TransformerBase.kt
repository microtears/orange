package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Proxy

class TransformerBase<S, T>(private val observer: ObserverBase<S, T>) : Transformer<S, T> {
    override fun transform(source: LiveData<S>): LiveData<T> {
        val result = MediatorLiveData<T>()
        val helper = ObserverHelper(
            observer,
            result::setValue,
            result::getValue,
            result::postValue,
            result::addSource,
            result::removeSource
        ) { source }
        observer.helper = helper
        result.addSource(source) {
            helper.onChanged(it)
        }
        return result;
    }

}