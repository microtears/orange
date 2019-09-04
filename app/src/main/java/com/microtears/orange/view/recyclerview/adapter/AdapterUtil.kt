package com.microtears.orange.view.recyclerview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.multiTypeAdapter(): MultiTypeAdapter? {
    return adapter as? MultiTypeAdapter
}

@Suppress("UNCHECKED_CAST")
fun <E, VH : ViewHolder> holderOf(itemView: View, block: (holder: VH, item: E) -> Unit): VH {
    return object : ViewHolder(itemView) {
        override fun <T> onBind(data: T) {
            @Suppress("UNCHECKED_CAST")
            block(this as VH, data as E)
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
    block: (holder: ViewHolder, item: T) -> Unit
): MultiTypeAdapter {
    val factory = object : ViewHolderFactory() {
        override fun create(type: Int, itemView: View): ViewHolder = holderOf(itemView, block)
    }
    return MultiTypeAdapter(factory, data)
}

fun <T : Any, VH : ViewHolder> adapterTypeOf(
    data: MutableList<Pair<Any, Int>> = mutableListOf(),
    block: (holder: VH, item: T) -> Unit
): MultiTypeAdapter {
    val factory = object : ViewHolderFactory() {
        override fun create(type: Int, itemView: View): VH = holderOf(itemView, block)
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
    block: (layoutId: Int, view: View) -> VH
): MultiTypeAdapter {
    return MultiTypeAdapter(factoryOf(block), data)
}