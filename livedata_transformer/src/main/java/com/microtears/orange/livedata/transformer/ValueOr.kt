package com.microtears.orange.livedata.transformer

data class ValueOr<T>(val value: T?, val throwable: Throwable?) {
    companion object {
        fun <E> fromThrowable(throwable: Throwable): ValueOr<E> {
            return ValueOr<E>(null, throwable);
        }

        fun <E> fromValue(value: E): ValueOr<E> {
            return ValueOr<E>(value, null);
        }
    }

    fun hasValue()=value!=null;
    fun hasError()=throwable!=null;
    
}
