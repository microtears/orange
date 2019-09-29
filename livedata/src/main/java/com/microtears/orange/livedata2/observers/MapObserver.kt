package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class MapObserver<S, T>(
    private val apply: (S) -> T,
    sync: Boolean = true
) : ObserverBase<S, T>(sync) {

    override fun onChanged(s: S) {
       setValue(apply(s))
    }
}