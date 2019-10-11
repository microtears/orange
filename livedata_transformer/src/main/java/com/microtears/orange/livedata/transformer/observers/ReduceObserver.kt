package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

open class ReduceObserver<S, T>(private var initValue: T, private var apply: (T, S) -> T) : Observer<S, T>() {
    override fun onChanged(t: S) {
        initValue = apply(initValue, t)
        setValue(initValue)
    }
}

fun <S, T> LiveData<S>.reduce(initValue: T, reduceFunction: (T, S) -> T): LiveData<T> {
    return TransformerImpl(ReduceObserver(initValue, reduceFunction)).transform(this)
}