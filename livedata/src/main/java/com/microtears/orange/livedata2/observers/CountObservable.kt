package com.microtears.orange.livedata2.observers

class CountObservable<S> : ReduceObservable<S, Int, Int>(0, { acc, _ -> acc + 1 })