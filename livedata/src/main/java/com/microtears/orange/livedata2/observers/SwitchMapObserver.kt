package com.microtears.orange.livedata2.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata2.ObserverBase

class SwitchMapObserver<S, T>(
    private val apply: (S) -> LiveData<T>,
    sync: Boolean = true
) : ObserverBase<S, T>(sync) {
    private var source: LiveData<T>? = null

    override fun onChanged(t: S) {
        val newLiveData = apply(t)
        if (source == newLiveData) return
        if (source != null)
            removeSource(source!!)
        source = newLiveData
        if (source != null) {
            addSource(source!!) {
                setValue(it)
            }
        }
    }
}