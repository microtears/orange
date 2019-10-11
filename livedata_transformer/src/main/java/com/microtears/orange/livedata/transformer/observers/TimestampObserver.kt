package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class TimestampObserver<S> : Observer<S, Pair<S, Long>>() {
    override fun onChanged(t: S) {
        val value = t to System.currentTimeMillis()
        setValue(value)
    }
}

fun <S> LiveData<S>.timestamp(): LiveData<Pair<S, Long>> {
    return TransformerImpl(TimestampObserver<S>()).transform(this)
}