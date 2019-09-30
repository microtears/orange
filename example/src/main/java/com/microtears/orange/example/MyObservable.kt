package com.microtears.orange.example

import android.util.Log
import androidx.lifecycle.LiveData
import com.microtears.orange.livedata.transformer.Observer
import com.microtears.orange.livedata.transformer.ObserverBase
import com.microtears.orange.livedata.transformer.TransformerBase

class MyObservable<S>(private val msg: String = "") : ObserverBase<S, S>() {

    companion object {

        @JvmStatic
        fun <S> lookValue(source: LiveData<S>, msg: String = ""): LiveData<S> {
            return TransformerBase<S, S>(MyObservable(msg)).transform(source)
        }
    }

    override fun onChanged(t: S) {
        Log.d("MyObservable", "$msg ## $t")
        setValue(t)
    }
}

fun <S> LiveData<S>.lookValue(msg: String = ""): LiveData<S> {
    return MyObservable.lookValue(this, msg)
}