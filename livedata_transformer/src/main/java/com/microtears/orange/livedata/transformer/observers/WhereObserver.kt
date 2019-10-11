package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class WhereObserver<S>(private val test: (S) -> Boolean) : Observer<S, S>() {
    override fun onChanged(t: S) {
        if (test(t))
            setValue(t)
    }
}

fun <S> LiveData<S>.where(testFunction: (S) -> Boolean): LiveData<S> {
    return TransformerImpl(WhereObserver(testFunction)).transform(this)
}