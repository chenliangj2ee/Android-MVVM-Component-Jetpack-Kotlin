package com.chenliang.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.chenliang.baselibrary.R

/**
 * 1-设置默认图
 * app:my_default="@drawable/default_header"
 * 2-设置宽高比
 * app:my_ratio="2:1"
 */

@SuppressLint("AppCompatCustomView")
class MyFrameLayout : FrameLayout {
    private var ratio: Float = 0.0F

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.MyFrameLayout)
        var ratioString = typedArray!!.getString(R.styleable.MyImageView_my_ratio)

        if (ratioString != null && ratioString.contains(":")) {
            ratio = ratioString.split(":")[1].toFloat() / ratioString.split(":")[0].toFloat()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (ratio != 0.0F) {
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            var heightMeasureSpec = MeasureSpec.makeMeasureSpec((widthSize * ratio).toInt(), MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}