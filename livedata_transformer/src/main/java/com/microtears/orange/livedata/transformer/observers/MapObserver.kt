package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class MapObserver<S, T>(private val apply: (S) -> T, sync: Boolean = true) : Observer<S, T>(sync) {

    override fun onChanged(s: S) {
        setValue(apply(s))
    }
}

fun <S, T> LiveData<S>.map(mapFunction: (S) -> T): LiveData<T> {
    return TransformerImpl(MapObserver(mapFunction)).transform(this)
}