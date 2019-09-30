package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Observable {
    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        var ignoreError: Boolean = false


        @JvmStatic
        fun <S> setError(source: MutableLiveData<S>, throwable: Throwable) {
            @Suppress("UNCHECKED_CAST")
            source.value =ValueOr.fromThrowable<S>(throwable) as S
        }

        @JvmStatic
        fun <S> observe(
            source: LiveData<S>,
            lifecycle: LifecycleOwner,
            observer: Observer<S>,
            errorObserver: Observer<Throwable>? = null
        ) {
            source.observe(lifecycle, Observer { t ->
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
                    if (!ignoreError)
                        errorObserver!!.onChanged(throwable)
                }
            })
        }
    }
}