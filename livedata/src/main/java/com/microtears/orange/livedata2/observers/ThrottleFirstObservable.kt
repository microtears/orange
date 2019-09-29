package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase
import java.util.concurrent.TimeUnit

class ThrottleFirstObservable<S>(
    private val timeValue: Long,
    private val timeUnit: TimeUnit
) : ObserverBase<S, S>() {
    private var previousTimeMillis = -1L
    override fun onChanged(t: S) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis >= previousTimeMillis + timeUnit.toMillis(timeValue)) {
            previousTimeMillis = currentTimeMillis
            setValue(t)
        }
    }
}