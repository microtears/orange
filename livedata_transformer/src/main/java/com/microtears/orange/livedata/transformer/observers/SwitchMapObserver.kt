package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.ObserverBase

class SwitchMapObserver<S, T>(
    private val apply: (S) -> LiveData<T>,
    sync: Boolean = true
) : ObserverBase<S, T>(sync) {
    private var mySource: LiveData<T>? = null

    override fun onChanged(t: S) {
        val newLiveData = apply(t)
        if (mySource == newLiveData) return
        if (mySource != null)
            removeSource(mySource!!)
        mySource = newLiveData
        if (mySource != null) {
            addSource(mySource!!) {
                setValue(it)
            }
        }
    }
}