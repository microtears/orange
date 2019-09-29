package com.microtears.orange.livedata.transformer.observers

class CountObservable<S> : ReduceObservable<S, Int, Int>(0, { acc, _ -> acc + 1 })