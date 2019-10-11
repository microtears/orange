package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class DistinctUntilChangedObserver<S> : Observer<S, S>() {

    override fun onChanged(currentValue: S) {
        val previousValue = getValue()
        if (currentValue != previousValue) {
            setValue(currentValue)
        }
    }
}

fun <S> LiveData<S>.distinctUntilChanged(): LiveData<S> {
    return TransformerImpl<S, S>(DistinctUntilChangedObserver()).transform(this)
}