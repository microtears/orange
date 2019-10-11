package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class TakeObserver<S>(private val size: Int) : Observer<S, S>() {
    private var count = 0
    override fun onChanged(t: S) {
        if (++count < size)
            setValue(t)
    }
}

fun <S> LiveData<S>.take(size: Int): LiveData<S> {
    return TransformerImpl(TakeObserver<S>(size)).transform(this)
}