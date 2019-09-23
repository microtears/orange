package com.microtears.orange.recyclerview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.multiTypeAdapter(): MultiTypeAdapter? {
    return adapter as? MultiTypeAdapter
}

@Suppress("UNCHECKED_CAST")
fun <E, VH : ViewHolder> holderOf(
    itemView: View,
    onPayload: ((holder: VH, item: E, payloads: MutableList<Any>) -> Unit)? = null,
    onBind: (holder: VH, item: E) -> Unit
): VH {
    return object : ViewHolder(itemView) {
        override fun <T> onBind(data: T) {
            @Suppress("UNCHECKED_CAST")
            onBind(this as VH, data as E)
        }

        override fun <T> onBind(data: T, payloads: MutableList<Any>) {
            if (onPayload == null) {
                super.onBind(data, payloads)
            } else {
                @Suppress("UNCHECKED_CAST")
                onPayload(this as VH, data as E, payloads)
            }
        }
    } as VH
}

fun <VH : ViewHolder> factoryOf(block: (Int, View) -> VH): ViewHolderFactory {
    return object : ViewHolderFactory() {
        override fun create(type: Int, itemView: View): ViewHolder = block(type, itemView)
    }
}

fun <T : Any> adapterOf(
    data: MutableList<Pair<Any, Int>> = mutableListOf(),
    onPayload: ((holder: ViewHolder, item: T, payloads: MutableList<Any>) -> Unit)? = null,
    onBind: (holder: ViewHolder, item: T) -> Unit
): MultiTypeAdapter {
    val factory = object : ViewHolderFactory() {
        override fun create(type: Int, itemView: View): ViewHolder =
            holderOf(itemView, onPayload, onBind)
    }
    return MultiTypeAdapter(factory, data)
}

fun <T : Any, VH : ViewHolder> adapterTypeOf(
    data: MutableList<Pair<Any, Int>> = mutableListOf(),
    onPayload: ((holder: VH, item: T, payloads: MutableList<Any>) -> Unit)? = null,
    onBind: (holder: VH, item: T) -> Unit
): MultiTypeAdapter {
    val factory = object : ViewHolderFactory() {
        override fun create(type: Int, itemView: View): VH = holderOf(itemView, onPayload, onBind)
    }
    return MultiTypeAdapter(factory, data)
}

fun multiAdapterOf(
    factory: ViewHolderFactory,
    data: MutableList<Pair<Any, Int>> = mutableListOf()
): MultiTypeAdapter {
    return MultiTypeAdapter(factory, data)
}

fun <VH : ViewHolder> multiAdapterOf(
    data: MutableList<Pair<Any, Int>> = mutableListOf(),
    onCreate: (layoutId: Int, view: View) -> VH
): MultiTypeAdapter {
    return MultiTypeAdapter(factoryOf(onCreate), data)
}