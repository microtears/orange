package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class TakeWhileObserver<S>(private val test: (S) -> Boolean) : Observer<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            setValue(t)
        else
            removeSource(getSource())
    }
}

fun <S> LiveData<S>.takeWhile(testFunction: (S) -> Boolean): LiveData<S> {
    return TransformerImpl(TakeWhileObserver(testFunction)).transform(this)
}