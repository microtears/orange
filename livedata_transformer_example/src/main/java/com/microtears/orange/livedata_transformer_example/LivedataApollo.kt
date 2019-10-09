package com.microtears.orange.livedata_transformer_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloQueryWatcher
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.microtears.orange.livedata.transformer.setError

object LivedataApollo {
    fun <T> from(watcher: ApolloQueryWatcher<T>): LiveData<T> {
        val result = MutableLiveData<T>()
        watcher.enqueueAndWatch(object : ApolloCall.Callback<T>() {
            override fun onFailure(e: ApolloException) {
                result.setError(e)
            }

            override fun onResponse(response: Response<T>) {
                if (response.hasErrors()) {
                    result.setError(ResponseException(response.errors()))
                }
                if (response.data() != null) {
                    result.postValue(response.data())
                }
            }
        })
        return result
    }
}