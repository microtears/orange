package com.microtears.orange.livedata

import java.util.*

class WeakHashSet<K> : MutableSet<K> {

    private val weakHashMap = WeakHashMap<K, Void>()

    override val size: Int = weakHashMap.size

    override fun iterator(): MutableIterator<K> = weakHashMap.keys.iterator()

    override fun contains(element: K): Boolean = weakHashMap.containsKey(element)

    override fun containsAll(elements: Collection<K>): Boolean {
        elements.forEach {
            if (!contains(it))
                return false
        }
        return true
    }


    override fun add(element: K): Boolean {
        return if (contains(element)) {
            false
        } else {
            weakHashMap[element] = null
            true
        }
    }

    override fun addAll(elements: Collection<K>): Boolean {
        var result = true
        elements.forEach {
            if (contains(it)) {
                result = false
                return@forEach
            }
        }
        if (result) {
            weakHashMap.putAll(elements.map { it to null })
        }
        return result
    }

    override fun remove(element: K): Boolean {
        return weakHashMap.remove(element) != null
    }

    override fun removeAll(elements: Collection<K>): Boolean {
        return if (containsAll(elements)) {
            elements.forEach { remove(it) }
            true
        } else {
            false
        }
    }

    override fun retainAll(elements: Collection<K>): Boolean {
        elements.forEach {
            if (!contains(it)) {
                return false
            }
        }
        return true
    }

    override fun clear() {
        weakHashMap.clear()
    }

    override fun isEmpty(): Boolean = weakHashMap.isEmpty()
}