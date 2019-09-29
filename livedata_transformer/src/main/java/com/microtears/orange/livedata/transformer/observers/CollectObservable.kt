package com.microtears.orange.livedata.transformer.observers

import com.microtears.orange.livedata.transformer.ObserverBase

class CollectObservable<S>(
    private val mutableCollection: MutableCollection<S>,
    private val collect: (collection: MutableCollection<S>, S) -> Unit = { collection, it ->
        collection.add(it)
    }
) : ObserverBase<S, MutableCollection<S>>() {
    override fun onChanged(t: S) {
        collect(mutableCollection, t)
        setValue(mutableCollection)
    }
}