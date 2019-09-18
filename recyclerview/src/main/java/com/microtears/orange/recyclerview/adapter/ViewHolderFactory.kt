package com.microtears.orange.recyclerview.adapter

import android.view.View

abstract class ViewHolderFactory {
    abstract fun create(type: Int, itemView: View): ViewHolder
}