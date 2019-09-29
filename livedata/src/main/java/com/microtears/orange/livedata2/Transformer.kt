package com.microtears.orange.livedata2

import androidx.lifecycle.LiveData

 interface Transformer<S,T> {
     fun transform(source:LiveData<S>):LiveData<T>
}