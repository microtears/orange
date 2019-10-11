package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class TypeObserver<S, T>(private val clazz: Class<T>) : Observer<S, T>() {
    override fun onChanged(t: S) {
        if (clazz.isInstance(t)) {
            @Suppress("UNCHECKED_CAST") val value = t as T
            setValue(value)
        }
    }
}

fun <S, T> LiveData<S>.type(clazz: Class<T>): LiveData<T> {
    return TransformerImpl<S, T>(TypeObserver(clazz)).transform(this)
}