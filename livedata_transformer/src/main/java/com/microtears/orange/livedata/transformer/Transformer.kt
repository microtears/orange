package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData

 interface Transformer<S,T> {
     fun transform(source:LiveData<S>):LiveData<T>
}