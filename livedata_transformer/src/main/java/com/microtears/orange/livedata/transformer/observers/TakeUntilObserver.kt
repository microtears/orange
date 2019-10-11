package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class TakeUntilObserver<S>(private val test: (S) -> Boolean) : Observer<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            removeSource(getSource())
        else
            setValue(t)
    }
}

fun <S> LiveData<S>.takeUntil(testFunction: (S) -> Boolean): LiveData<S> {
    return TransformerImpl(TakeUntilObserver(testFunction)).transform(this)
}