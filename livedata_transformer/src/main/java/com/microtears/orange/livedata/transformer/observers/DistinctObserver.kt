package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer
import java.util.*

class DistinctObserver<S, T>(private val keys: (S) -> T) : Observer<S, S>() {

    private val set: MutableSet<T> = Collections.newSetFromMap(WeakHashMap<T, Boolean>())

    override fun onChanged(t: S) {
        val key = keys(t)
        if (!set.contains(key)) {
            set.add(key)
            setValue(t)
        }
    }
}

fun <S> LiveData<S>.distinct(): LiveData<S> = distinct { it }

fun <S, T> LiveData<S>.distinct(keys: (S) -> T): LiveData<S> {
    return TransformerImpl(DistinctObserver(keys)).transform(this)
}