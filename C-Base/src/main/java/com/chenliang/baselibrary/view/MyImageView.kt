package com.chenliang.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.load

/**
<com.chenliang.baselibrary.view.MyImageView
    bindLoad="@{bean.coverImage}" 绑定图片，适用ImageView及其子类
    bindRadius="@{12}"  绑定图片圆角，适用ImageView及其子类
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:my_default="@drawable/icon_relax_text_bg"  设置默认图，仅适用MyImageView
    app:my_radius="12" 设置图片圆角，仅适用MyImageView
    app:my_ratio="1:1"  设置图片宽高比，仅适用MyImageView
/>
 */

@SuppressLint("AppCompatCustomView")
class MyImageView : ImageView {
    var default = R.drawable.load_default
    private var ratio: Float = 0.0F
    var radius = 0;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.MyImageView)
        default = typedArray!!.getResourceId(R.styleable.MyImageView_my_default, -1)
        radius = typedArray!!.getInteger(R.styleable.MyImageView_my_radius, 0)
        var ratioString = typedArray!!.getString(R.styleable.MyImageView_my_ratio)
        if (ratioString != null && ratioString.contains(":")) {
            ratio = ratioString.split(":")[1].toFloat() / ratioString.split(":")[0].toFloat()
        }

        if (default != -1) {
            if (radius > 0)
                load(default, radius)
            else
                setImageResource(default)
        }

    }


    fun setImageColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageTintList = ColorStateList.valueOf(color)
        }
    }

    fun setImageColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageTintList = ColorStateList.valueOf(Color.parseColor(color))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (ratio != 0.0F) {
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            var heightMeasureSpec =
                MeasureSpec.makeMeasureSpec((widthSize * ratio).toInt(), MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}