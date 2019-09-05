package com.microtears.orange.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        fun create(): LiveDataCallAdapterFactory {
            return LiveDataCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*, *>? {
        if (returnType !is ParameterizedType) {
            return null
        }
        val returnClass = getRawType(returnType)
        if (returnClass != LiveData::class.java) {
            return null
        }
        val type = getParameterUpperBound(0, returnType)
        return LiveDataCallAdapter<Any>(type)
    }

    class LiveDataCallAdapter<R>(var type: Type) : CallAdapter<R, LiveData<R>> {

        override fun adapt(call: Call<R>): LiveData<R> {
            return object : LiveData<R>() {
                val flag = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (flag.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {
                            override fun onFailure(call: Call<R>, t: Throwable) {
                                if (BuildConfig.DEBUG) {
                                    Log.e("LiveDataCallAdapter", "onFailure: ", t)
                                }
                            }

                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                if (BuildConfig.DEBUG) {
                                    Log.d("LiveDataCallAdapter", "onResponse: CALL=$call")
                                    Log.d("LiveDataCallAdapter", "onResponse: RESPONSE=$response")
                                }
                                if (response.isSuccessful)
                                    postValue(response.body())
                                else {
                                    if (BuildConfig.DEBUG) {
                                        Log.e(
                                            "LiveDataCallAdapter",
                                            "onResponse :FAILURE :CALL=$call :RESPONSE=$response"
                                        )
                                    }

                                }
                            }
                        })
                    }
                }

                override fun onInactive() {
                    super.onInactive()
                    if (!call.isCanceled)
                        call.cancel()
                }
            }
        }

        override fun responseType(): Type {
            return type
        }
    }

}