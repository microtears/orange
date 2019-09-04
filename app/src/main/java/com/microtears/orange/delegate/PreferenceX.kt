package com.microtears.orange.delegate

import kotlin.reflect.KProperty

class PreferenceX<T>(
    default: T,
    private val getValueBefore: ((thisRef: Any?, property: KProperty<*>) -> Unit)? = null,
    private val setValueBefore: ((thisRef: Any?, property: KProperty<*>, value: T) -> Unit)? = null
) : Preference<T>(default) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        getValueBefore?.invoke(thisRef, property)
        return super.getValue(thisRef, property)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValueBefore?.invoke(thisRef, property, value)
        super.setValue(thisRef, property, value)
    }
}