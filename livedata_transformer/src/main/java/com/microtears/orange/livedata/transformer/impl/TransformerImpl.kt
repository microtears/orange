package com.microtears.orange.livedata.transformer.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.microtears.orange.livedata.transformer.interfaces.Observer
import com.microtears.orange.livedata.transformer.interfaces.Transformer

class TransformerImpl<S, T>(private val observer: Observer<S, T>) :
    Transformer<S, T> {
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
        return result
    }

}