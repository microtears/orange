package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class CastObserver<S, T> : Observer<S, T>() {
    override fun onChanged(t: S) {
        @Suppress("UNCHECKED_CAST")
        val value = t as T
        setValue(value)
    }
}

fun <S, T> LiveData<S>.cast(): LiveData<T> {
    return TransformerImpl(CastObserver<S, T>()).transform(this)
}