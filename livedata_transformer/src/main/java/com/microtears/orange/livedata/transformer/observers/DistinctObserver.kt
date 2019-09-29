package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.WeakHashSet
import com.microtears.orange.livedata.transformer.ObserverBase

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