package com.microtears.orange.glide.transition.colormatrix

import android.animation.ValueAnimator
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.bumptech.glide.request.transition.Transition

class ColorMatrixTransition(private val duration: Long) : Transition<Drawable> {

    private val normalInterpolator by lazy { LinearInterpolator() }

    override fun transition(current: Drawable?, adapter: Transition.ViewAdapter?): Boolean {
        adapter?.view?.takeIf { current != null && it is ImageView }?.let { view ->
            val colorMatrix = ColorMatrix()
            view as ImageView
            view.setImageDrawable(current)
            ValueAnimator.ofFloat(0f, 1f).apply {
                addUpdateListener {
                    val value = it.animatedValue as Float
                    colorMatrix.setSaturation(value)
                    view.colorFilter = ColorMatrixColorFilter(colorMatrix)
                }
                interpolator = normalInterpolator
            }.setDuration(duration).start()
        }
        return true
    }
}