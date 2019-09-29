package com.microtears.orange.livedata2.observers

import com.microtears.orange.livedata.WeakHashSet
import com.microtears.orange.livedata2.ObserverBase

class DistinctObserver<S, K>(
    private val keys: (S) -> K
) : ObserverBase<S, S>() {


    val set = WeakHashSet<K>()
    override fun onChanged(t: S) {
        val key = keys(t)
        if (!set.contains(key)) {
            set.add(key)
            setValue(t)
        }
    }
}