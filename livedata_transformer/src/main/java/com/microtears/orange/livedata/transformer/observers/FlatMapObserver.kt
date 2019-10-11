package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class FlatMapObserver<S, T>(private val apply: (S) -> LiveData<T>, sync: Boolean = true) : Observer<S, T>(sync) {
    override fun onChanged(s: S) {
        val newLiveData = apply(s)
        addSource(newLiveData) {
            setValue(it)
        }
    }
}


fun <S, T> LiveData<S>.flatMap(flatMapFunction: (S) -> LiveData<T>): LiveData<T> {
    return TransformerImpl(FlatMapObserver(flatMapFunction)).transform(this)
}