package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata2.ObserverBase

class MapIndexObserver<S, T>(
    private val apply: (Int, S) -> T,
    sync: Boolean = true
) : ObserverBase<S, T>(sync) {
    private var index = -1

    override fun onChanged(s: S) {
        setValue(apply(++index, s))
    }
}