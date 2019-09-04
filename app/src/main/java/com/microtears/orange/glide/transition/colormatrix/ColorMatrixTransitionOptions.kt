package com.microtears.orange.glide.transition.colormatrix

import android.graphics.drawable.Drawable
import com.bumptech.glide.TransitionOptions

class ColorMatrixTransitionOptions : TransitionOptions<ColorMatrixTransitionOptions, Drawable>() {


    fun saturationFade(duration: Long): ColorMatrixTransitionOptions {
        return transition(ColorMatrixTransitionFactory(duration))
    }

    companion object {
        @JvmStatic
        fun withSaturationFade(duration: Long = 2000): ColorMatrixTransitionOptions {
            return ColorMatrixTransitionOptions().saturationFade(duration)
        }
    }
}