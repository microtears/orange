package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class SkipNullObserver<S>() : Observer<S, S>() {
    override fun onChanged(t: S?) {
        if (t != null)
            setValue(t)
    }
}

fun <S> LiveData<S>.skipNull(): LiveData<S> {
    return TransformerImpl(SkipNullObserver<S>()).transform(this)
}