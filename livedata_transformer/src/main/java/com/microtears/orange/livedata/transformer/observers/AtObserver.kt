package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class AtObserver<S>(private val index: Int) : Observer<S, S>() {
    private var count = -1
    override fun onChanged(t: S) {
        if (++count == index)
            setValue(t)
    }
}

fun <S> LiveData<S>.at(index: Int): LiveData<S> {
    return TransformerImpl(AtObserver<S>(index)).transform(this)
}