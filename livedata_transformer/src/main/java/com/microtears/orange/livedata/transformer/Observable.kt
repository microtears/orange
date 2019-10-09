package com.microtears.orange.livedata.transformer

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Observable {
    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        var ignoreError: Boolean = false

        @Suppress("MemberVisibilityCanBePrivate")
        var errorHandler: UncaughtExceptionHandler = object : UncaughtExceptionHandler {
            override fun <T> uncaughtException(source: LiveData<T>, throwable: Throwable) {
                throw  throwable
            }
        }

        @JvmStatic
        fun <S> setError(source: MutableLiveData<S>, throwable: Throwable) {
            @Suppress("UNCHECKED_CAST")
            val value = ValueOr.fromThrowable<S>(throwable) as S
            if (Looper.getMainLooper().thread != Thread.currentThread())
                source.postValue(value)
            else
                source.value = value
        }

        @JvmStatic
        fun <S> observe(
            source: LiveData<S>,
            lifecycle: LifecycleOwner,
            observer: Observer<S>,
            errorObserver: Observer<Throwable>? = null
        ) {
            source.observe(lifecycle, getObserver(observer, errorObserver, source))
        }

        private fun <S> getObserver(
            observer: Observer<S>,
            errorObserver: Observer<Throwable>?,
            source: LiveData<S>
        ): Observer<S> {
            return Observer { t ->
                try {
                    if (t is ValueOr<*>) {
                        if (t.hasError()) {
                            throw t.throwable!!
                        } else {
                            if (t.hasValue()) {
                                @Suppress("UNCHECKED_CAST")
                                observer.onChanged(t.value!! as S)
                            }
                        }
                    } else {
                        observer.onChanged(t)
                    }
                } catch (throwable: Throwable) {
                    if (!ignoreError) {
                        if (errorObserver != null) {
                            errorObserver.onChanged(throwable)
                        } else {
                            errorHandler.uncaughtException(source, throwable)
                        }
                    }

                }
            }
        }

        @JvmStatic
        fun <S> observeForever(
            source: LiveData<S>,
            lifecycle: LifecycleOwner,
            observer: Observer<S>,
            errorObserver: Observer<Throwable>? = null
        ): Observer<S> {
            val result = getObserver(observer, errorObserver, source)
            source.observeForever(result)
            return result
        }


    }
}