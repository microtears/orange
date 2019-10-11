package com.microtears.orange.livedata.transformer.observers

import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.impl.TransformerImpl
import com.microtears.orange.livedata.transformer.interfaces.Observer

class CollectObserver<S>(
    private val mutableCollection: MutableCollection<S>,
    private val collect: (collection: MutableCollection<S>, S) -> Unit = { collection, it ->
        collection.add(it)
    }
) : Observer<S, Collection<S>>() {
    override fun onChanged(t: S) {
        collect(mutableCollection, t)
        setValue(mutableCollection)
    }
}

fun <S> LiveData<S>.collect(
    mutableCollection: MutableCollection<S> = mutableListOf(),
    collectFunction: (collection: MutableCollection<S>, S) -> Unit = { collection, it -> collection.add(it) }
): LiveData<Collection<S>> {
    return TransformerImpl(CollectObserver(mutableCollection, collectFunction)).transform(this)
}