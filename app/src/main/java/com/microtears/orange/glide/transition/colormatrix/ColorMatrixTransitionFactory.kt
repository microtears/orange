package com.microtears.orange.glide.transition.colormatrix

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.transition.NoTransition
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory

class ColorMatrixTransitionFactory(private val duration: Long) : TransitionFactory<Drawable> {
    private val colorMatrixTransition by lazy { ColorMatrixTransition(duration) }

    override fun build(dataSource: DataSource, isFirstResource: Boolean): Transition<Drawable> {
        return if (!isFirstResource || dataSource == DataSource.MEMORY_CACHE) NoTransition.get()
        else colorMatrixTransition
    }

}