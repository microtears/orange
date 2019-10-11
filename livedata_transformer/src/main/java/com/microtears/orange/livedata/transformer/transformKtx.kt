@file:Suppress("unused")

package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <S> LiveData<S>.from(collection: Collection<S>): LiveData<S> {
    val result = MutableLiveData<S>()
    collection.forEach(result::setValue)
    return result
}
