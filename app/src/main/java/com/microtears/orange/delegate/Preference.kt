package com.microtears.orange.delegate

import android.app.Application
import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "unused")
open class Preference<T>(/*val context: Context,*//* val name: String,*/ private val default: T) :
    ReadWriteProperty<Any?, T> {

    constructor(filename: String, default: T) : this(default) {
        this.filename = filename
    }


    companion object {
        fun init(application: Application) {
            Companion.application = application
        }

        private var application: Application? = null
        private val context get() = application!!
    }

    private var filename: String? = null

    private val prefs by lazy {
        if (filename != null) {
            context.getSharedPreferences(
                filename,
                Context.MODE_PRIVATE
            )
        } else {
            context.getSharedPreferences(
                context.javaClass.simpleName/*"default"*/,
                Context.MODE_PRIVATE
            )
        }

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(property.name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(property.name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        if (default is Set<*> /*&& default.any { it is String }*/) {
            return@with getStringSet(name, default as Set<String>) as T
        }
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }

        res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        if (default is Set<*> /*&& default.any { it is String }*/) {
            putStringSet(name, value as Set<String>).apply()
            return@with
        }
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}