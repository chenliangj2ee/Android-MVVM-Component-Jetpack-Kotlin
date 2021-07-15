package com.chenliang.mvvmc.demo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/15
 */
class MyButton : View {

    var cx = 0
    var cy = 0

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initWH()
    }

    fun initWH() {
        if (cx == 0) {
            postDelayed(Runnable {
                cx = (x + width / 2).toInt()
                cy = (y + height / 2).toInt()

                if (width == 0) {
                    initWH()
                }

            }, 100)
        }
    }


    fun start(mW: Int, mH: Int) {
        var scale = mW / width
        var scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, scale.toFloat())
        var scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, scale.toFloat())


        var cxx = ObjectAnimator.ofFloat(this, "cx", cx.toFloat(), (mW / 2).toFloat())
        var cyy = ObjectAnimator.ofFloat(this, "cy", cy.toFloat(), (mH / 2).toFloat())


        var an = AnimatorSet()
        an.playTogether(scaleX, scaleY,cxx,cyy)
        an.duration = 5000
        an.start()
    }
}