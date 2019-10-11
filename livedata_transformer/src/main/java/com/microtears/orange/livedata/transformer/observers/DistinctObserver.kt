package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer
import com.microtears.orange.livedata.transformer.util.WeakHashSet

class DistinctObserver<S, K>(private val keys: (S) -> K) : Observer<S, S>() {

    val set = WeakHashSet<K>()
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