package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl

class CountObserver<S> : ReduceObserver<S, Int>(0, { acc, _ -> acc + 1 })

fun <S> LiveData<S>.count(): LiveData<Int> {
    return TransformerImpl<S, Int>(CountObserver()).transform(this)
}
