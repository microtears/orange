package com.microtears.orange.livedata2

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import javax.xml.transform.Source

class Observable {
    companion object {
        var ignoreError: Boolean = false

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