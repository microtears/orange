package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class MapIndexObserver<S, T>(private val apply: (Int, S) -> T, sync: Boolean = true) : Observer<S, T>(sync) {
    private var index = -1

    override fun onChanged(s: S) {
        setValue(apply(++index, s))
    }
}

fun <S, T> LiveData<S>.mapIndex(mapIndexFunction: (Int, S) -> T): LiveData<T> {
    return TransformerImpl(MapIndexObserver(mapIndexFunction)).transform(this)
}