package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer
import java.util.concurrent.TimeUnit

class ThrottleFirstObserver<S>(private val timeValue: Long, private val timeUnit: TimeUnit) : Observer<S, S>() {
    private var previousTimeMillis = -1L

    override fun onChanged(t: S) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis >= previousTimeMillis + timeUnit.toMillis(timeValue)) {
            previousTimeMillis = currentTimeMillis
            setValue(t)
        }
    }
}

fun <S> LiveData<S>.throttleFirst(timeValue: Long, timeUnit: TimeUnit): LiveData<S> {
    return TransformerImpl<S, S>(ThrottleFirstObserver(timeValue, timeUnit)).transform(this)
}