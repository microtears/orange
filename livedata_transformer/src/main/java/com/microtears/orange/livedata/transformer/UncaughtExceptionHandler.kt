package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData

interface UncaughtExceptionHandler {
    fun <T>uncaughtException(source:LiveData<T>,throwable: Throwable)
}