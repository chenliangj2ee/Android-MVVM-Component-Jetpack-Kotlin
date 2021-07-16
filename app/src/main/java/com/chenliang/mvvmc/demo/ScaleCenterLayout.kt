package com.chenliang.mvvmc.demo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chenliang.baselibrary.utils.log
import java.nio.file.Files.size


/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/15
 */
class ScaleCenterLayout : FrameLayout {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initWH()
    }


    var defaultWidth = 0
    var defaultHeight = 0;

    var centerX = 0f

    var centerY = 0f

    var defaultCenterX = 0f

    var defaultCenterY = 0f

    var myWidth = 0

    var myHeight = 0


    fun initWH() {
        if (centerX == 0f) {
            postDelayed({
                centerX = (x + width / 2).toFloat()
                centerY = (y + height / 2).toFloat()

                defaultWidth = width
                defaultHeight = height

                defaultCenterX = centerX
                defaultCenterY = centerY
                if (width == 0) {
                    initWH()
                }
            }, 200)
        }
    }
    fun initWH2() {
        centerX = (x + width / 2).toFloat()
        centerY = (y + height / 2).toFloat()

        defaultWidth = width
        defaultHeight = height

        defaultCenterX = centerX
        defaultCenterY = centerY
        if (width == 0) {
            initWH()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    var boo = true

    fun start(mW: Int, mH: Int) {
//        getChildAt(0).visibility= View.GONE
        initWH2()
        if (boo) {
            boo = !boo
            val scale = mW.toFloat() / width.toFloat()
            val widthX = ObjectAnimator.ofInt(this, "myWidth", width, (width * scale).toInt())
            val heightY = ObjectAnimator.ofInt(this, "myHeight", height, mH)
            val cxx = ObjectAnimator.ofFloat(this, "centerX", centerX, (mW / 2).toFloat())
            val cyy = ObjectAnimator.ofFloat(this, "centerY", centerY, (mH / 2).toFloat())


            cyy.addUpdateListener {
                val p = layoutParams
                p.width = myWidth
                p.height = myHeight

                x = centerX - width / 2
                y = centerY - height / 2
                layoutParams = p
                postInvalidate()
//                invalidate()
                log("myWidth---$myWidth-----------x:$x")

            }
            val an = AnimatorSet()
            an.playTogether(widthX, heightY, cxx, cyy)
            an.duration = 500
            an.start()

        } else {
            boo = !boo


            val scale = mW.toFloat() / defaultWidth.toFloat()
            val widthX = ObjectAnimator.ofInt(this, "myWidth", width, defaultWidth)
            val heightY = ObjectAnimator.ofInt(this, "myHeight", height, defaultHeight)
            val cxx = ObjectAnimator.ofFloat(this, "centerX", (mW / 2).toFloat(), defaultCenterX)
            val cyy = ObjectAnimator.ofFloat(this, "centerY", (mH / 2).toFloat(), defaultCenterY)


            cyy.addUpdateListener {
                val p = layoutParams
                p.width = myWidth
                p.height = myHeight

                x = centerX - width / 2
                y = centerY - height / 2
                layoutParams = p

            }
            val an = AnimatorSet()
            an.playTogether(widthX, heightY, cxx, cyy)
            an.duration = 500
            an.start()

        }
    }

    var paint=Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.isAntiAlias=true
        log("ondraw------------------------------ $myWidth")
        canvas!!.drawColor(Color.BLUE)

        paint.color = Color.BLACK
        canvas!!.drawCircle((myWidth/2).toFloat(), (myHeight/2).toFloat(), myWidth.toFloat()/2,paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        paint.color = Color.YELLOW
        canvas!!.drawRect(0f, 1f, (width).toFloat(), (height).toFloat(),paint)
        paint.xfermode = null


    }
}