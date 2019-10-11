package com.microtears.orange.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveDataOf(default: T? = null): MutableLiveData<T> {
    return default?.let { MutableLiveData(default) } ?: MutableLiveData()
}

fun <T> mediatorLiveDataOf(default: T? = null): MediatorLiveData<T> {
    return default?.let { MediatorLiveData<T>().apply { value = it } } ?: MediatorLiveData()
}
