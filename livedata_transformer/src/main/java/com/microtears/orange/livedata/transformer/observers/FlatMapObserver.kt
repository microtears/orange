package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.ObserverBase

class FlatMapObserver<S, T>(
    private val apply: (S) -> LiveData<T>,
    sync: Boolean = true
) : ObserverBase<S, T>(sync) {
    override fun onChanged(s: S) {
        val newLiveData = apply(s)
        addSource(newLiveData) {
            setValue(it)
        }
    }
}